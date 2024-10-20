package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.ProductRequest;
import choiyh.musinsabackendassignment.service.ProductService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories/lowest-prices")
    @Description("1. 카테고리별 최저 가격 브랜드와 상품 가격, 총액을 조회합니다.")
    public ResponseEntity<?> getLowestPriceBrandByCategory() {
        Map<String, Object> result = productService.getLowestPriceBrandByCategory();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories/{category}/lowest-highest")
    @Description("3. 카테고리명으로 최저, 최고 가격 브랜드와 상품 가격을 조회합니다.")
    public ResponseEntity<?> getLowestHighestBrandByCategory(@PathVariable String category) {
        Map<String, Object> result = productService.getLowestHighestBrandByCategory(category);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Description("4. 상품을 추가합니다.")
    public ResponseEntity<?> add(@RequestBody ProductRequest request) {
        Long id = productService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping
    @Description("4. 상품을 업데이트합니다.")
    public ResponseEntity<?> update() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Description("4. 상품을 삭제합니다.")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok().build();
    }

}
