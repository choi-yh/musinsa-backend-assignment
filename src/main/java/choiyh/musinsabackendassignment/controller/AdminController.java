package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.brand.AddBrandRequest;
import choiyh.musinsabackendassignment.dto.product.AddProductRequest;
import choiyh.musinsabackendassignment.dto.brand.UpdateBrandRequest;
import choiyh.musinsabackendassignment.dto.product.UpdateProductRequest;
import choiyh.musinsabackendassignment.service.BrandService;
import choiyh.musinsabackendassignment.service.ProductService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final BrandService brandService;
    private final ProductService productService;

    @PostMapping("/brands")
    @Description("4. 브랜드를 추가합니다.")
    public ResponseEntity<?> addBrand(@RequestBody AddBrandRequest request) {
        Long id = brandService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/brands/{id}")
    @Description("4. 브랜드를 업데이트합니다.")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody UpdateBrandRequest request) {
        brandService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/brands/{id}")
    @Description("4. 브랜드를 삭제합니다.")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    @Description("4. 상품을 추가합니다.")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest request) {
        Long id = productService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/products/{id}")
    @Description("4. 상품을 업데이트합니다.")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        productService.update(id, request);
        return ResponseEntity.noContent().build(); // 결과를 알려줄 필요는 없다고 판단하여 204 처리
    }

    @DeleteMapping("/products/{id}")
    @Description("4. 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
