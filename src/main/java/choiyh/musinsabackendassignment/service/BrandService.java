package choiyh.musinsabackendassignment.service;

import choiyh.musinsabackendassignment.dto.BrandRequest;
import choiyh.musinsabackendassignment.dto.ProductRequest;
import choiyh.musinsabackendassignment.entity.Brand;
import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.repository.BrandRepository;
import choiyh.musinsabackendassignment.util.PriceUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    // TODO: DTO 정의
    public Map<String, Object> getLowestPriceByBrand() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

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

        Map<String, Object> categoryValues = lowestPriceBrand.getProducts().stream()
                .collect(Collectors.toMap(Product::getCategory, p -> PriceUtil.priceFormattingWithComma(p.getPrice())));

        data.put("brand", lowestPriceBrand.getName());
        data.put("total_price", totalPrice);
        data.put("category", categoryValues);

        result.put("lowest_price", data);

        return result;
    }

    @Transactional
    public Long add(BrandRequest request) {
        // TODO: 중복된 브랜드명 처리는 고민해 볼 것
        List<Product> products = new ArrayList<>();
        if (request.getProducts() != null) {
            products = request.getProducts().stream()
                    .map(ProductRequest::toEntity)
                    .toList();
        }

        Brand brand = Brand.builder()
                .name(request.getName())
                .products(products)
                .build();
        brandRepository.save(brand);

        return brand.getId();
    }

}
