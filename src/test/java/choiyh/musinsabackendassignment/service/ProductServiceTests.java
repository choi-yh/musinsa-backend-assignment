package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.product.UpdateProductRequest;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.repository.ProductRepository;
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
    @DisplayName("4. 제품 업데이트 비즈니스 로직 성공 케이스 - 정상 업데이트")
    public void update_product_success() {
        // given
        Product product = Product.builder()
                .category(Category.TOP)
                .price(10000)
                .build();

        String expectedCategory = Category.BAG.toString();
        Integer expectedPrice = 12000;

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setCategory(Category.BAG.toString());
        updateProductRequest.setPrice(12000);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        // when
        productService.update(1L, updateProductRequest);

        // then
        assertEquals(expectedCategory, product.getCategory().toString());
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    @DisplayName("4. 제품 업데이트 비즈니스 로직 실패 케이스 - 존재하지 않는 brand id. CustomException 발생")
    public void update_product_fail() {
        // given
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setCategory(Category.BAG.toString());
        updateProductRequest.setPrice(12000);

        when(productRepository.findById(any())).thenThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND));

        // when, then
        CustomException e = assertThrows(CustomException.class, () -> productService.update(1L, updateProductRequest));
        assertEquals(ErrorCode.NOT_EXIST_BRAND, e.getErrorCode());
    }

}
