package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.product.AddProductRequest;
import choiyh.musinsabackendassignment.dto.brand.UpdateBrandRequest;
import choiyh.musinsabackendassignment.dto.product.UpdateProductRequest;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.service.BrandService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("4. 브랜드 수정 API 성공 케이스 - 204 응답")
    public void update_brand_success() throws Exception {
        // given
        doNothing().when(brandService).update(any(Long.class), any(UpdateBrandRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateBrandRequest()))
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 브랜드 수정 API 실패 케이스 - 400 NOT_EXIST_BRAND 응답")
    public void update_brand_fail() throws Exception {
        // given
        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandService).update(any(Long.class), any(UpdateBrandRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/admin/brands/1")
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
    public void delete_brand_success() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(brandService).delete(id);

        // when
        ResultActions response = mockMvc.perform(
                delete("/api/v1/admin/brands/" + id)
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 브랜드 삭제 API 실패 케이스 - 400 NOT_EXIST_BRAND 반환")
    public void delete_brand_fail() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(brandService).delete(id);
        doThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND)).when(brandService).delete(id);

        // when
        ResultActions response = mockMvc.perform(
                delete("/api/v1/admin/brands/" + id)
        );

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not existing brand"));
    }


    @Test
    @DisplayName("4. 상품 추가 API 성공 케이스 - 201, Location header 응답")
    public void add_product_success() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(1L);

        when(productService.add(any(AddProductRequest.class))).thenReturn(1L);
        Long expectedProductId = 1L;

        // when
        ResultActions response = mockMvc.perform(
                post("/api/v1/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/api/v1/admin/products/" + expectedProductId));
    }

    @Test
    @DisplayName("4. 상품 추가 API 실패 케이스 - 존재하지 않는 브랜드 id. 400 NOT_EXIST_BRAND 응답")
    public void add_product_fail_with_invalid_brand_id() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(100L);

        when(productService.add(any(AddProductRequest.class))).thenThrow(new CustomException(ErrorCode.NOT_EXIST_BRAND));

        // when
        ResultActions response = mockMvc.perform(
                post("/api/v1/admin/products")
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
    public void update_product_success() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doNothing().when(productService).update(any(Long.class), any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/admin/products/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 단일 상품 업데이트 API 실패 케이스 - 존재하지 않는 상품 id. 400 NOT_EXIST_PRODUCT 응답")
    public void update_product_fail_with_invalid_product_id() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doThrow(new CustomException(ErrorCode.NOT_EXIST_PRODUCT)).when(productService).update(any(Long.class), any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(
                patch("/api/v1/admin/products/" + 1L)
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
    public void delete_product_success() throws Exception {
        // given
        Long targetProductId = 1L;
        doNothing().when(productService).delete(targetProductId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/admin/products/" + targetProductId));

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 상품 삭제 API 실패 케이스 - 존재하지 않는 상품 id. 400 NOT_EXIST_PRODUCT 응답")
    public void delete_product_fail_with_invalid_product_id() throws Exception {
        // given
        Long targetProductId = 1L;
        doThrow(new CustomException(ErrorCode.NOT_EXIST_PRODUCT)).when(productService).delete(targetProductId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/admin/products/" + targetProductId));

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not exist product"));
    }

}
