package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.brand.LowestPriceByBrandResponse;
import choiyh.musinsabackendassignment.service.BrandService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/lowest-price")
    @Description("2. 단일 브랜드 구매시 최저가인 브랜드 정보를 조회합니다.")
    public LowestPriceByBrandResponse getLowestPriceByBrand() {
        return brandService.getLowestPriceByBrand();
    }

}
