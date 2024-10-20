package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.AddProductRequest;
import choiyh.musinsabackendassignment.dto.UpdateProductRequest;
import choiyh.musinsabackendassignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
    @DisplayName("4. 상품 추가 API 성공 케이스")
    @Description("Location header 를 반환합니다.")
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
    @DisplayName("4. 상품 추가 API 실패 케이스")
    @Description("Invalid brand id 예외를 반환합니다.")
    public void add_fail_with_invalid_brand_id() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(100L);

        // TODO: error handling
        when(productService.add(any(AddProductRequest.class))).thenThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다."));

        // when
        ResultActions response = mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        // TODO: custom exception 정의 후 해당 값과 코드로 비교할 것.
        response.andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("4. 단일 상품 업데이트 API 성공 케이스")
    @Description("204 상태 코드를 응답합니다.")
    public void update_success() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doNothing().when(productService).update(1L, any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(patch("/api/v1/products/" + 1L));

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("4. 단일 상품 업데이트 API 실패 케이스")
    @Description("") // TODO: exception handling
    public void update_fail_with_invalid_product_id() throws Exception {
        // given
        UpdateProductRequest request = new UpdateProductRequest();
        doThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다.")).when(productService).update(1L, any(UpdateProductRequest.class));

        // when
        ResultActions response = mockMvc.perform(patch("/api/v1/products/" + 1L));

        // then
        response.andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException())); // TODO: exception handling
    }

    @Test
    @DisplayName("4. 상품 삭제 API 성공 케이스")
    @Description("204 상태 코드를 응답합니다.")
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
    @DisplayName("4. 상품 삭제 API 실패 케이스")
    @Description("") // TODO: exception handling
    public void delete_fail_with_invalid_product_id() throws Exception {
        // given
        Long targetProductId = 1L;
        doThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다.")).when(productService).delete(targetProductId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/products/" + targetProductId));

        // then
        response.andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException())); // TODO: exception handling
    }

}