package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.service.ProductService;
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

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 테스트")
    public void getLowestPriceBrandByCategory() throws Exception {
        // when
        ResultActions response = mockMvc.perform(
                get("/api/v1/products/categories/lowest-prices")
        );

        // then
        response.andExpect(status().isOk());
        verify(productService, times(1)).getLowestPriceBrandByCategory();
    }

    @Test
    @DisplayName("3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 테스트")
    public void getLowestHighestBrandByCategory() throws Exception {
        // given
        String category = "상의";

        // when
        ResultActions response = mockMvc.perform(
                get(String.format("/api/v1/products/categories/%s/lowest-highest", category))
        );

        // then
        response.andExpect(status().isOk());
        verify(productService, times(1)).getLowestHighestPriceBrandByCategory(category);
    }

}