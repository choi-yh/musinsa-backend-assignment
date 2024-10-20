package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("4. 브랜드 삭제 비즈니스 로직 - 성공 케이스")
    @Description("브랜드 및 제품을 정상적으로 삭제합니다.")
    public void delete_success() {
        // given
        Product product1 = Product.builder()
                .category(Category.TOP)
                .price(10000)
                .build();
        Product product2 = Product.builder()
                .category(Category.OUTER)
                .price(32000)
                .build();

        Long brandId = 1L;
        Brand brand = Brand.builder()
                .name("brand name")
                .products(List.of(product1, product2))
                .build();

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        brandService.delete(brandId);

        // then
        Mockito.verify(brandRepository, times(1)).delete(brand);
        // TODO: 제품 삭제 로직 검증?
    }

    @Test
    @DisplayName("4. 브랜드 삭제 비즈니스 로직 - 실패 케이스")
    @Description("존재하지 않는 브랜드 ID로 인해 예외를 던집니다.")
    public void delete_fail_with_invalid_brandId() {
        // given
        Long brandId = 1L;
        Brand brand = Brand.builder()
                .name("brand name")
                .products(new ArrayList<>())
                .build();

        doThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다.")).when(brandRepository).delete(brand); // TODO: error handling

        // when
        brandService.delete(brandId);

        // then
        assertThrows(IllegalArgumentException.class, () -> brandService.delete(1L)); // TODO: error handling
    }

}
