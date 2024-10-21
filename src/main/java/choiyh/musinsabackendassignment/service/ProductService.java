package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.*;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import choiyh.musinsabackendassignment.repository.ProductRepository;
import choiyh.musinsabackendassignment.util.PriceUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    @Transactional
    public LowestPriceBrandByCategoryResponse getLowestPriceBrandByCategory() {
        LowestPriceBrandByCategoryResponse result = new LowestPriceBrandByCategoryResponse();
        Integer total = 0;

        List<ProductDto> productsResponse = new ArrayList<>();

        // TODO: for loop 돌지 않고 빠르게 처리 할 방법 고민 (카테고리가 매우 많은 경우)
        for (Category category : Category.values()) {
            List<Product> products = productRepository.findByCategory(category); // TODO: category 별 product 가 많아지는 경우 처리 방안 고민
            Product lowestProduct = products.stream()
                    .min(Comparator.comparing(Product::getPrice))
                    .orElse(null); // 해당 카테고리에 데이터가 없는 경우 빈 데이터 리턴

            if (lowestProduct != null) {
                ProductDto data = ProductDto.of(lowestProduct);
                productsResponse.add(data);

                total += lowestProduct.getPrice();
            }
        }

        result.setProducts(productsResponse);
        result.setTotal(PriceUtil.priceFormattingWithComma(total));

        return result;
    }

    @Transactional
    public LowestHighestPriceBrandByCategoryResponse getLowestHighestPriceBrandByCategory(String category) {
        LowestHighestPriceBrandByCategoryResponse result = new LowestHighestPriceBrandByCategoryResponse();

        List<Product> products = productRepository.findByCategory(Category.getCategoryFromKorean(category));
        if (products.isEmpty()) {
            // TODO: 주어진 카테고리가 아닌 경우에 대한 처리 필요. (예외 발생)
            return result;
        }

        // TODO: 가독성 좋게 리팩토링
        Product lowestProduct = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .get();

        ProductDto lowestData = new ProductDto();
        lowestData.setBrand(lowestProduct.getBrand().getName());
        lowestData.setPrice(PriceUtil.priceFormattingWithComma(lowestProduct.getPrice()));

        Product highestProduct = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .get(); // TODO: check

        ProductDto highestData = new ProductDto();
        highestData.setBrand(highestProduct.getBrand().getName());
        highestData.setPrice(PriceUtil.priceFormattingWithComma(highestProduct.getPrice()));

        result.setLowestPrice(lowestData);
        result.setCategory(category);
        result.setHighestPrice(highestData);

        return result;
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
        // TODO: 최적화 방안 모색 - for loop 처리, request 들어온 값을 업데이트하는 방법
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

}
