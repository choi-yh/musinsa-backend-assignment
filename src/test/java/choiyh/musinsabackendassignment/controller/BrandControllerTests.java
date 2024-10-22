package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.UpdateBrandRequest;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("4. 브랜드 수정 API 성공 케이스 - 204 응답")
    public void update_success() throws Exception {
        // given
        doNothing().when(brandService).update(any(Long.class), any(UpdateBrandRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateBrandRequest()))
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 브랜드 수정 API 실패 케이스 - 400 NOT_EXIST_BRAND 응답")
    public void update_fail() throws Exception {
        // given
        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandService).update(any(Long.class), any(UpdateBrandRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateBrandRequest()))
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not existing brand"));
    }

    @Test
    @DisplayName("4. 브랜드 삭제 API 성공 케이스 - 204 응답")
    public void delete_success() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(brandService).delete(id);

        // when
        ResultActions response = mockMvc.perform(
                delete("/api/v1/brands/" + id)
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 브랜드 삭제 API 실패 케이스 - 400 NOT_EXIST_BRAND 반환")
    public void delete_fail() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(brandService).delete(id);
        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandService).delete(id);

        // when
        ResultActions response = mockMvc.perform(
                delete("/api/v1/brands/" + id)
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not existing brand"));
    }
}
