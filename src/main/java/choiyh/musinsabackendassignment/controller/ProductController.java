package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.product.LowestHighestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.dto.product.LowestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product API", description = "1,3번 문제에 대한 API를 제공합니다.")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories/lowest-prices")
    @Operation(summary = "1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    })
    public LowestPriceBrandByCategoryResponse getLowestPriceBrandByCategory() {
        return productService.getLowestPriceBrandByCategory();
    }

    @GetMapping("/categories/{category}/lowest-highest")
    @Operation(summary = "3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 카테고리 요청", content = @Content(mediaType = "application/json"))
    })
    public LowestHighestPriceBrandByCategoryResponse getLowestHighestBrandByCategory(
            @Parameter(description = "조회 할 카테고리명", example = "상의")
            @PathVariable String category
    ) {
        Category categoryValue = Category.getCategoryFromKorean(category);
        return productService.getLowestHighestPriceBrandByCategory(categoryValue);
    }

}
