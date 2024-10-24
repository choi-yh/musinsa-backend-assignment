package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @DisplayName("3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 테스트 - 성공 케이스")
    public void getLowestHighestBrandByCategory_success() throws Exception {
        // given
        String category = "상의";

        // when
        ResultActions response = mockMvc.perform(
                get(String.format("/api/v1/products/categories/%s/lowest-highest", category))
        );

        // then
        response.andExpect(status().isOk());
        verify(productService, times(1)).getLowestHighestPriceBrandByCategory(Category.getCategoryFromKorean(category));
    }

    @Test
    @DisplayName("3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 테스트 - 잘못된 카테고리 입력")
    public void getLowestHighestBrandByCategory_fail_with_invalid_category() throws Exception {
        // given
        String category = "향수";

        // when
        ResultActions response = mockMvc.perform(
                get(String.format("/api/v1/products/categories/%s/lowest-highest", category))
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unknown category" + " " + category));
    }

}