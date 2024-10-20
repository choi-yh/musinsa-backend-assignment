package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.ProductRequest;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        ProductRequest request = new ProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(1L);

        when(productService.add(any(ProductRequest.class))).thenReturn(1L);
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
        ProductRequest request = new ProductRequest();
        request.setCategory("상의");
        request.setPrice(10000);
        request.setBrandId(100L);

        // TODO: error handling
        when(productService.add(any(ProductRequest.class))).thenThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다."));

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

}