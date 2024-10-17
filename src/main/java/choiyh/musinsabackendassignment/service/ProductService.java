package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.entity.Product;
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

        List<String> categories = List.of("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"); // TODO: enum 으로 관리 할 것
        // TODO: for loop 돌지 않고 빠르게 처리 할 방법 고민 (카테고리가 매우 많은 경우)
        for (String category : categories) {
            List<Product> products = productRepository.findByCategory(category); // TODO: category 별 product 가 많아지는 경우 처리 방안 고민
            Product lowestProduct = products.stream()
                    .min(Comparator.comparing(Product::getPrice))
                    .orElseThrow(() -> new RuntimeException("해당 카테고리 상품이 없습니다.")); // TODO: exception handling.

            Map<String, Object> data = new HashMap<>();
            data.put("category", category);
            data.put("brand", lowestProduct.getBrand().getName());
            data.put("price", PriceUtil.priceFormattingWithComma(lowestProduct.getPrice()));

            list.add(data);

            total += lowestProduct.getPrice();
        }

        result.put("products", list);
        result.put("total", PriceUtil.priceFormattingWithComma(total));

        return result;
    }

}
