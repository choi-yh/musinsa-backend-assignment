package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.product.*;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import choiyh.musinsabackendassignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public LowestPriceBrandByCategoryResponse getLowestPriceBrandByCategory() {
        LowestPriceBrandByCategoryResponse result = new LowestPriceBrandByCategoryResponse();
        Integer totalPrice = 0;

        List<ProductDto> productsResponse = new ArrayList<>();

        for (Category category : Category.values()) {
            Product lowestProduct = getLowestPriceProductForCategory(category);
            if (lowestProduct != null) {
                ProductDto data = ProductDto.of(lowestProduct);
                productsResponse.add(data);

                totalPrice += lowestProduct.getPrice();
            }
        }

        result.setProducts(productsResponse);
        result.setTotalPrice(totalPrice);

        return result;
    }

    public Product getLowestPriceProductForCategory(Category category) {
        int page = 0;
        int size = 1000;
        Product lowestProduct = null;

        while (true) {
            Page<Product> pagedProducts = productRepository.findByCategory(category, PageRequest.of(page, size));

            List<Product> products = pagedProducts.getContent();
            if (products.isEmpty()) {
                break;
            }

            Product lowestProductInPage = products.stream()
                    .min(Comparator.comparing(Product::getPrice))
                    .orElse(null);

            if (lowestProduct == null || lowestProductInPage.getPrice() < lowestProduct.getPrice()) {
                lowestProduct = lowestProductInPage;
            }

            if (pagedProducts.isLast()) {
                break;
            }

            page++;
        }

        return lowestProduct;
    }

    @Transactional(readOnly = true)
    public LowestHighestPriceBrandByCategoryResponse getLowestHighestPriceBrandByCategory(Category category) throws CustomException {
        LowestHighestPriceBrandByCategoryResponse result = new LowestHighestPriceBrandByCategoryResponse();

        List<Product> products = productRepository.findByCategory(category);

        ProductDto lowestData = filterLowestPriceProduct(products);
        ProductDto highestData = filterHighestPriceProduct(products);

        result.setCategory(category.toString());
        result.setLowestPrice(lowestData);
        result.setHighestPrice(highestData);

        return result;
    }

    private ProductDto filterLowestPriceProduct(List<Product> products) {
        Product lowestProduct = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .get(); // 문제 조건에서 브랜드의 카테고리에는 1개의 상품이 존재한다고 하여 null check 생략

        return ProductDto.ofWithoutCategory(lowestProduct);
    }

    private ProductDto filterHighestPriceProduct(List<Product> products) {
        Product highestProduct = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .get(); // 문제 조건에서 브랜드의 카테고리에는 1개의 상품이 존재한다고 하여 null check 생략

        return ProductDto.ofWithoutCategory(highestProduct);
    }

    @Transactional
    public Long add(AddProductRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_BRAND));

        Product product = Product.builder()
                .category(request.getCategory())
                .price(request.getPrice())
                .build();

        product.mappingBrand(brand);

        productRepository.save(product);

        return product.getId();
    }

    @Transactional
    public void update(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));

        if (request.getCategory() != null) {
            product.updateCategory(request.getCategory());
        }

        if (request.getPrice() != null) {
            product.updatePrice(request.getPrice());
        }
    }

    @Transactional
    public void updateBulk(Brand brand, List<UpdateProductRequest> requests) {
        for (UpdateProductRequest request : requests) {
            Optional<Product> existProduct = brand.getProducts().stream()
                    .filter(p -> p.getId().equals(request.getId()))
                    .findFirst();

            // 존재하는 경우 값을 업데이트
            if (existProduct.isPresent()) {
                Product product = existProduct.get();
                if (request.getCategory() != null) {
                    product.updateCategory(request.getCategory());
                }

                if (request.getPrice() != null) {
                    product.updatePrice(request.getPrice());
                }
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));
        productRepository.delete(product);
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::of)
                .toList();
    }

}
