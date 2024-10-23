package choiyh.musinsabackendassignment.controller;

import choiyh.musinsabackendassignment.dto.brand.AddBrandRequest;
import choiyh.musinsabackendassignment.dto.brand.UpdateBrandRequest;
import choiyh.musinsabackendassignment.dto.product.AddProductRequest;
import choiyh.musinsabackendassignment.dto.product.ProductDto;
import choiyh.musinsabackendassignment.dto.product.UpdateProductRequest;
import choiyh.musinsabackendassignment.service.BrandService;
import choiyh.musinsabackendassignment.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin API", description = "4번 문제에 대한 API를 제공합니다.")
public class AdminController {

    private final BrandService brandService;
    private final ProductService productService;

    @PostMapping("/brands")
    @Operation(summary = "4. 브랜드(및 상품)를 추가하는 API", description = "상품은 여러건 추가 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> addBrand(@RequestBody AddBrandRequest request) {
        Long id = brandService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/brands/{id}")
    @Operation(summary = "4. 브랜드(및 상품)를 업데이트하는 API", description = "브랜드 명, 해당 브랜드 상품(카테고리, 가격)을 업데이트할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 브랜드 id", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> updateBrand(
            @Parameter(description = "주어진 브랜드 ID는 1~9 입니다.")
            @PathVariable Long id,
            @RequestBody UpdateBrandRequest request) {
        brandService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/brands/{id}")
    @Operation(summary = "4. 브랜드(와 해당 상품)을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 브랜드 id", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> deleteBrand(
            @Parameter(description = "주어진 브랜드 ID는 1~9 입니다.")
            @PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    @Operation(summary = "4. 단일 상품을 추가하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest request) {
        Long id = productService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/products/{id}")
    @Operation(summary = "4. 단일 상품을 업데이트하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 상품 id", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        productService.update(id, request);
        return ResponseEntity.noContent().build(); // 결과를 알려줄 필요는 없다고 판단하여 204 처리
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "4. 단일 상품을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 상품 id", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    @Operation(summary = "프론트엔드 페이지에서 보여주기 위한 데이터를 가져오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

}
