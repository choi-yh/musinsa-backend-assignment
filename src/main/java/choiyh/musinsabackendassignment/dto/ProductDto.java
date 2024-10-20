package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.util.PriceUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
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
