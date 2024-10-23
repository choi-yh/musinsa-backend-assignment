package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.brand.LowestPriceByBrandResponse;
import choiyh.musinsabackendassignment.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
@Tag(name = "Brand API", description = "2번 문제에 대한 API를 제공합니다.")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/lowest-price")
    @Operation(summary = "2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    })
    public LowestPriceByBrandResponse getLowestPriceByBrand() {
        return brandService.getLowestPriceByBrand();
    }

}
