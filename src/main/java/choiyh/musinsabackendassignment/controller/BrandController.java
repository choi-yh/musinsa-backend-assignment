package choiyh.musinsabackendassignment.controller;

import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    @GetMapping("/lowest-price")
    @Description("2. 단일 브랜드 구매시 최저가인 브랜드 정보를 조회합니다.")
    public ResponseEntity<?> getLowestPriceByBrand() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Description("4. 브랜드를 추가합니다.")
    public ResponseEntity<?> add() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Description("4. 브랜드를 업데이트합니다.")
    public ResponseEntity<?> update() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Description("4. 브랜드를 삭제합니다.")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok().build();
    }

}
