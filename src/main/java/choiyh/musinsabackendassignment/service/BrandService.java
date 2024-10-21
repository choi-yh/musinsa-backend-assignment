package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.*;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import choiyh.musinsabackendassignment.util.PriceUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final ProductService productService;

    public LowestPriceByBrandResponse getLowestPriceByBrand() {
        LowestPriceByBrandResponse result = new LowestPriceByBrandResponse();
        LowestPriceByBrand data = new LowestPriceByBrand();

        List<Brand> brands = brandRepository.findAll(); // TODO: 현재는 데이터가 적지만, 데이터가 많아지게 되는 경우 고려 할 것

        // TODO: 데이터 핸들링 방식 가독성 좋게 리팩토링
        // TODO: N+1 이슈 처리
        Brand lowestPriceBrand = brands.stream()
                .min(Comparator.comparing(b -> b.getProducts().stream().mapToInt(Product::getPrice).sum()))
                .orElse(null); // TODO: null 처리

        if (lowestPriceBrand == null) {
            return result;
        }

        Integer totalPrice = lowestPriceBrand.getProducts().stream()
                .mapToInt(Product::getPrice)
                .sum();

        List<ProductDto> categoryValues = lowestPriceBrand.getProducts().stream()
                .map(
                        p -> ProductDto.builder()
                                .category(p.getCategory().toString())
                                .price(PriceUtil.priceFormattingWithComma(p.getPrice()))
                                .build()
                )
                .toList();

        data.setBrand(lowestPriceBrand.getName());
        data.setTotal(PriceUtil.priceFormattingWithComma(totalPrice));
        data.setProducts(categoryValues);

        result.setLowestPrice(data);

        return result;
    }

    @Transactional
    public Long add(AddBrandRequest request) {
        // TODO: 중복된 브랜드명 처리는 고민해 볼 것
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: error handling

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 brand id 입니다.")); // TODO: error handling
        brandRepository.delete(brand);
    }

}
