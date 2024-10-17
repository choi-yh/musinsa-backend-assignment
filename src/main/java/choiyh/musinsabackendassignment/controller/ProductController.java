package choiyh.musinsabackendassignment.controller;

import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/categories/lowest-prices")
    @Description("1. 카테고리별 최저 가격 브랜드와 상품 가격, 총액을 조회합니다.")
    public ResponseEntity<?> getLowestPriceBrandByCategory() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories/{category}/lowest-highest")
    @Description("3. 카테고리명으로 최저, 최고 가격 브랜드와 상품 가격을 조회합니다.")
    public ResponseEntity<?> getLowestHighestBrandByCategory(@PathVariable String category) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Description("4. 상품을 추가합니다.")
    public ResponseEntity<?> add() {
        return ResponseEntity.ok().build();
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
