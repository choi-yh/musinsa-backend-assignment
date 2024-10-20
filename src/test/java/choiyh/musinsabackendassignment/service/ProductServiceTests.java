package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.UpdateProductRequest;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.repository.ProductRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("제품 업데이트 테스트")
    @Description("정상적으로 업데이트 되는 케이스입니다.")
    public void update_product_success() {
        // given
        Product product = Product.builder()
                .category(Category.TOP)
                .price(10000)
                .build();

        String expectedCategory = Category.BAG.getKorean();
        Integer expectedPrice = 12000;

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setCategory(Category.BAG.getKorean());
        updateProductRequest.setPrice(12000);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        // when
        productService.update(1L, updateProductRequest);

        // then
        assertEquals(expectedCategory, product.getCategory().getKorean());
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    @DisplayName("제품 업데이트 테스트")
    @Description("존재하지 않는 id 예외를 던지는 케이스입니다.")
    public void update_product_fail() {
        // given
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setCategory(Category.BAG.getKorean());
        updateProductRequest.setPrice(12000);

        when(productRepository.findById(any())).thenThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: exception handling

        // when, then
        assertThrows(IllegalArgumentException.class, () -> productService.update(1L, updateProductRequest));
    }

}
