package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.AddProductRequest;
import choiyh.musinsabackendassignment.dto.UpdateProductRequest;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.service.ProductService;
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

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("4. 상품 추가 API 성공 케이스 - 201, Location header 응답")
    public void add_success() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(1L);

        when(productService.add(any(AddProductRequest.class))).thenReturn(1L);
        Long expectedProductId = 1L;

        // when
        ResultActions response = mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/api/v1/products/" + expectedProductId));
    }

    @Test
    @DisplayName("4. 상품 추가 API 실패 케이스 - 존재하지 않는 브랜드 id. 400 NOT_EXIST_BRAND 응답")
    public void add_fail_with_invalid_brand_id() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(100L);

        when(productService.add(any(AddProductRequest.class))).thenThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND));

        // when
        ResultActions response = mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not existing brand"));
    }

    @Test
    @DisplayName("4. 단일 상품 업데이트 API 성공 케이스 - 204 응답")
    public void update_success() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doNothing().when(productService).update(any(Long.class), any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/products/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 단일 상품 업데이트 API 실패 케이스 - 존재하지 않는 상품 id. 400 NOT_EXIST_PRODUCT 응답")
    public void update_fail_with_invalid_product_id() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doThrow(new CustomException(ErrorCode.NOT_EXIST_PRODUCT)).when(productService).update(any(Long.class), any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/products/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not exist product"));
    }

    @Test
    @DisplayName("4. 상품 삭제 API 성공 케이스 - 204 응답")
    public void delete_success() throws Exception {
        // given
        Long targetProductId = 1L;
        doNothing().when(productService).delete(targetProductId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/products/" + targetProductId));

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 상품 삭제 API 실패 케이스 - 존재하지 않는 상품 id. 400 NOT_EXIST_PRODUCT 응답")
    public void delete_fail_with_invalid_product_id() throws Exception {
        // given
        Long targetProductId = 1L;
        doThrow(new CustomException(ErrorCode.NOT_EXIST_PRODUCT)).when(productService).delete(targetProductId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/products/" + targetProductId));

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not exist product"));
    }

}