package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
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

    // TODO: DTO 정의
    @Transactional
    public Map<String, Object> getLowestPriceBrandByCategory() {
        Map<String, Object> result = new HashMap<>();
        Integer total = 0;

        List<Map<String, Object>> list = new ArrayList<>();

        // TODO: for loop 돌지 않고 빠르게 처리 할 방법 고민 (카테고리가 매우 많은 경우)
        for (Category category : Category.values()) {
            List<Product> products = productRepository.findByCategory(category); // TODO: category 별 product 가 많아지는 경우 처리 방안 고민
            Product lowestProduct = products.stream()
                    .min(Comparator.comparing(Product::getPrice))
                    .orElseThrow(() -> new RuntimeException("해당 카테고리 상품이 없습니다.")); // TODO: exception handling.

            Map<String, Object> data = new HashMap<>();
            data.put("category", category.toString());
            data.put("brand", lowestProduct.getBrand().getName());
            data.put("price", PriceUtil.priceFormattingWithComma(lowestProduct.getPrice()));

            list.add(data);

            total += lowestProduct.getPrice();
        }

        result.put("products", list);
        result.put("total", PriceUtil.priceFormattingWithComma(total));

        return result;
    }

    // TODO: DTO 정의
    @Transactional
    public Map<String, Object> getLowestHighestBrandByCategory(String category) {
        Map<String, Object> result = new HashMap<>();
        result.put("category", category);

        List<Product> products = productRepository.findByCategory(Category.getCategoryFromKorean(category));

        if (products.isEmpty()) {
            // TODO: 주어진 카테고리가 아닌 경우에 대한 처리 필요. (예외 발생)
            return result;
        }

        // TODO: 가독성 좋게 리팩토링
        Product lowestProduct = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .get();
        Map<String, String> lowestData = new HashMap<>();
        lowestData.put("brand", lowestProduct.getBrand().getName());
        lowestData.put("price", PriceUtil.priceFormattingWithComma(lowestProduct.getPrice()));

        result.put("lowest_price", lowestData);

        Product highestProduct = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .get();

        Map<String, String> highestData = new HashMap<>();
        highestData.put("brand", highestProduct.getBrand().getName());
        highestData.put("price", PriceUtil.priceFormattingWithComma(highestProduct.getPrice()));

        result.put("highest_price", highestData);


        return result;
    }

}
