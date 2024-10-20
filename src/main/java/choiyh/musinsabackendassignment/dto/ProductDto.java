package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.util.PriceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductDto {

    private String category;
    private String brand;
    private String price;

    public static ProductDto of(Product product) {
        return ProductDto.builder()
                .category(product.getCategory().toString())
                .brand(product.getBrand().getName())
                .price(PriceUtil.priceFormattingWithComma(product.getPrice()))
                .build();
    }
}
