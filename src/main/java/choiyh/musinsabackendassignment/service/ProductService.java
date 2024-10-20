package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.*;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
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
                    .orElseThrow(() -> new RuntimeException("해당 카테고리 상품이 없습니다.")); // TODO: exception handling.

            ProductDto data = ProductDto.of(lowestProduct);
            productsResponse.add(data);

            total += lowestProduct.getPrice();
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: error handling

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: error handling

        if (request.getCategory() != null) {
            product.updateCategory(request.getCategory());
        }

        if (request.getPrice() != null) {
            product.updatePrice(request.getPrice());
        }
    }

    // TODO: 브랜드 업데이트시 bulk 로 업데이트하는 로직

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: error handling
        productRepository.delete(product);
    }

}
