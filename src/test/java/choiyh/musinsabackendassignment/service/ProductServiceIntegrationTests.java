package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.LowestHighestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.dto.LowestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 비즈니스 로직 테스트")
    public void getLowestPriceBrandByCategory() {
        // given
        ProductDto top = new ProductDto("상의", "C", "10,000");
        ProductDto outer = new ProductDto("아우터", "E", "5,000");
        ProductDto pants = new ProductDto("바지", "D", "3,000");
        ProductDto sneakersA = new ProductDto("스니커즈", "A", "9,000");
        ProductDto sneakersG = new ProductDto("스니커즈", "G", "9,000");
        ProductDto bag = new ProductDto("가방", "A", "2,000");
        ProductDto hat = new ProductDto("모자", "D", "1,500");
        ProductDto socks = new ProductDto("양말", "I", "1,700");
        ProductDto accessory = new ProductDto("액세서리", "F", "1,900");

        // when
        LowestPriceBrandByCategoryResponse result = productService.getLowestPriceBrandByCategory();

        // then
        assertEquals("34,100", result.getTotalPrice());

        assertEquals(top, result.getProducts().stream().filter(product -> product.getCategory().equals("상의")).findFirst().orElse(new ProductDto()));
        assertEquals(outer, result.getProducts().stream().filter(product -> product.getCategory().equals("아우터")).findFirst().orElse(new ProductDto()));
        assertEquals(pants, result.getProducts().stream().filter(product -> product.getCategory().equals("바지")).findFirst().orElse(new ProductDto()));
        assertThat(
                result.getProducts().stream().filter(product -> product.getCategory().equals("스니커즈")).findFirst().orElse(new ProductDto()),
                anyOf(is(sneakersA), is(sneakersG))
        );
        assertEquals(bag, result.getProducts().stream().filter(product -> product.getCategory().equals("가방")).findFirst().orElse(new ProductDto()));
        assertEquals(hat, result.getProducts().stream().filter(product -> product.getCategory().equals("모자")).findFirst().orElse(new ProductDto()));
        assertEquals(socks, result.getProducts().stream().filter(product -> product.getCategory().equals("양말")).findFirst().orElse(new ProductDto()));
        assertEquals(accessory, result.getProducts().stream().filter(product -> product.getCategory().equals("액세서리")).findFirst().orElse(new ProductDto()));
    }

    @Test
    @DisplayName("3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 비즈니스 로직 테스트")
    public void getLowestHighestPriceBrandByCategory() {
        // given
        String category = "상의";

        // when
        LowestHighestPriceBrandByCategoryResponse result = productService.getLowestHighestPriceBrandByCategory(category);

        // then
        assertEquals(category, result.getCategory());

        assertEquals("C", result.getLowestPrice().getBrand());
        assertEquals("10,000", result.getLowestPrice().getPrice());

        assertEquals("I", result.getHighestPrice().getBrand());
        assertEquals("11,400", result.getHighestPrice().getPrice());

    }

}
