package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    @DisplayName("2. 단일 브랜드로 모든 카테고리 상품 구매 시 최저가 브랜드와 카테고리별 상품 가격, 총액을 조회하는 API 테스트")
    public void getLowestPriceByBrand() throws Exception {
        // when
        ResultActions response = mockMvc.perform(
                get("/api/v1/brands/lowest-price")
        );

        // then
        response.andExpect(status().isOk());
        verify(brandService, times(1)).getLowestPriceByBrand();
    }

}
