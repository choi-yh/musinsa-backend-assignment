package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.brand.LowestPriceByBrandResponse;
import choiyh.musinsabackendassignment.dto.product.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BrandServiceTests {

    @Autowired
    private BrandService brandService;

    @Test
    @DisplayName("2. 단일 브랜드로 모든 카테고리 상품 구매 시 최저가 브랜드와 카테고리별 상품 가격, 총액을 조회하는 로직 테스트")
    public void getLowestPriceByBrand() {
        // given
        Map<String, String> expectedCategoryPrices = Map.of(
                "상의", "10,100",
                "아우터", "5,100",
                "바지", "3,000",
                "스니커즈", "9,500",
                "가방", "2,500",
                "모자", "1,500",
                "양말", "2,400",
                "액세서리", "2,000"
        );

        // when
        LowestPriceByBrandResponse result = brandService.getLowestPriceByBrand();

        // then
        assertEquals("36,100", result.getLowestPrice().getTotalPrice());
        assertEquals("D", result.getLowestPrice().getBrand());

        assertEquals(expectedCategoryPrices, result.getLowestPrice().getProducts().stream()
                .collect(Collectors.toMap(ProductDto::getCategory, ProductDto::getPrice)));
    }

}
