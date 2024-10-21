package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.UpdateBrandRequest;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.repository.BrandRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("4. 브랜드 업데이트 비즈니스 로직 성공 케이스 - 브랜드 이름 업데이트")
    public void update_only_brandName() {
        // given
        Long brandId = 1L;
        Brand brand = Brand.builder()
                .name("original name")
                .products(new ArrayList<>())
                .build();

        String updatedName = "updated name";
        UpdateBrandRequest request = new UpdateBrandRequest();
        request.setName(updatedName);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        brandService.update(brandId, request);

        // then
        assertEquals(updatedName, brand.getName());
    }

    @Test
    @DisplayName("4. 브랜드 업데이트 비즈니스 로직 실패 케이스 - 존재하지 않는 brand id. CustomException 발생")
    public void update_fail_by_invalid_brandId() {
        // given
        Long brandId = 1L;

        String updatedName = "updated name";
        UpdateBrandRequest request = new UpdateBrandRequest();
        request.setName(updatedName);

        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandRepository).findById(any(Long.class));

        // when & then
        CustomException e = assertThrows(CustomException.class, () -> brandService.update(brandId, request));
        assertEquals(ErrorCode.NOT_EXIST_BRAND, e.getErrorCode());
    }

    @Test
    @DisplayName("4. 브랜드 삭제 비즈니스 로직 성공 케이스 - 브랜드 및 제품을 정상적으로 삭제")
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
    @DisplayName("4. 브랜드 삭제 비즈니스 로직 실패 케이스 - 존재하지 않는 brand id. CustomException 발생")
    public void delete_fail_with_invalid_brandId() {
        // given
        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandRepository).findById(any(Long.class));

        // when & then
        CustomException e = assertThrows(CustomException.class, () -> brandService.delete(1L));
        assertEquals(ErrorCode.NOT_EXIST_BRAND, e.getErrorCode());
    }

}
