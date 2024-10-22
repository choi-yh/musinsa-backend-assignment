package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.product.LowestHighestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.dto.product.LowestPriceBrandByCategoryResponse;
import choiyh.musinsabackendassignment.service.ProductService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories/lowest-prices")
    @Description("1. 카테고리별 최저 가격 브랜드와 상품 가격, 총액을 조회합니다.")
    public LowestPriceBrandByCategoryResponse getLowestPriceBrandByCategory() {
        return productService.getLowestPriceBrandByCategory();
    }

    @GetMapping("/categories/{category}/lowest-highest")
    @Description("3. 카테고리명으로 최저, 최고 가격 브랜드와 상품 가격을 조회합니다.")
    public LowestHighestPriceBrandByCategoryResponse getLowestHighestBrandByCategory(@PathVariable String category) {
        return productService.getLowestHighestPriceBrandByCategory(category);
    }

}
