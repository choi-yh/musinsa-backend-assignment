package choiyh.musinsabackendassignment;

import choiyh.musinsabackendassignment.controller.BrandController;
import choiyh.musinsabackendassignment.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MusinsaBackendAssignmentApplicationTests {

    @Autowired
    private BrandController brandController;

    @Autowired
    ProductController productController;

    @Test
    void contextLoads() {
        assertThat(brandController).isNotNull();
        assertThat(productController).isNotNull();
    }

}
