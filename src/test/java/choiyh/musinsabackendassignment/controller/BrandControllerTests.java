package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.service.BrandService;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    @DisplayName("4. 브랜드 삭제 API - 성공 케이스")
    @Description("204 No Content 응답")
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
    @DisplayName("4. 브랜드 삭제 API - 실패 케이스")
    @Description("") // TODO: exception handling
    public void delete_fail() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(brandService).delete(id);
        doThrow(new IllegalArgumentException("존재하지 않는 brand id 입니다.")).when(brandService).delete(id);

        // when
        ResultActions response = mockMvc.perform(
                delete("/api/v1/brands/" + id)
        );

        // then
        response.andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException())); // TODO: exception handling
    }

}
