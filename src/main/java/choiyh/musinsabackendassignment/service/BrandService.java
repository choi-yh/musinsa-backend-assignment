package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.*;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import choiyh.musinsabackendassignment.util.PriceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final ProductService productService;

    @Transactional(readOnly = true)
    public LowestPriceByBrandResponse getLowestPriceByBrand() {
        int page = 0;
        int size = 1000;
        Brand lowestPriceBrand = null;

        while (true) {
            Page<Brand> pagedBrands = brandRepository.findAllWithProducts(PageRequest.of(page, size));

            List<Brand> brands = pagedBrands.getContent();
            if (brands.isEmpty()) {
                break;
            }

            lowestPriceBrand = findLowestPriceBrand(brands, lowestPriceBrand);

            if (pagedBrands.isLast()) {
                break;
            }
            page++;
        }

        return createResponse(lowestPriceBrand);
    }

    // 페이징으로 찾은 브랜드 중에서 총액이 최저인 브랜드를 찾고, 현재 최저 총액인 브랜드와 비교하여 리턴하는 메서드입니다.
    private Brand findLowestPriceBrand(List<Brand> brands, Brand currentLowestBrand) {
        return brands.stream()
                .min(Comparator.comparing(this::calculateBrandTotalPrice))
                .filter(pageLowest ->
                        currentLowestBrand == null ||
                                calculateBrandTotalPrice(pageLowest) < calculateBrandTotalPrice(currentLowestBrand))
                .orElse(currentLowestBrand);
    }

    // 브랜드 상품의 카테고리별 상품 정보 데이터를 생성합니다.
    private LowestPriceByBrandResponse createResponse(Brand lowestPriceBrand) {
        LowestPriceByBrandResponse result = new LowestPriceByBrandResponse();
        if (lowestPriceBrand == null) {
            return result;
        }

        List<ProductDto> categoryValues = lowestPriceBrand.getProducts().stream()
                .map(ProductDto::ofWithoutBrand)
                .toList();

        Integer totalPrice = calculateBrandTotalPrice(lowestPriceBrand);

        LowestPriceByBrand data = new LowestPriceByBrand();
        data.setBrand(lowestPriceBrand.getName());
        data.setTotalPrice(PriceUtil.priceFormattingWithComma(totalPrice));
        data.setProducts(categoryValues);

        result.setLowestPrice(data);

        return result;
    }

    private Integer calculateBrandTotalPrice(Brand brand) {
        return brand.getProducts().stream()
                .mapToInt(Product::getPrice)
                .sum();
    }

    @Transactional
    public Long add(AddBrandRequest request) {
        List<Product> products = new ArrayList<>();
        if (request.getProducts() != null) {
            products = request.getProducts().stream()
                    .map(AddProductRequest::toEntity)
                    .toList();
        }

        Brand brand = Brand.builder()
                .name(request.getName())
                .products(products)
                .build();
        brandRepository.save(brand);

        return brand.getId();
    }

    @Transactional
    public void update(Long id, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_BRAND));

        // 브랜드 명 업데이트
        if (request.getName() != null && !request.getName().isBlank()) {
            brand.updateName(request.getName());
        }

        // 브랜드 상품 업데이트
        if (request.getProducts() != null && !request.getProducts().isEmpty()) {
            productService.updateBulk(brand, request.getProducts());
        }
    }

    @Transactional
    public void delete(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_BRAND));
        brandRepository.delete(brand);
    }

}
