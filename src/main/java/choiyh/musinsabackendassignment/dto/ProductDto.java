package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.util.PriceUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, brand, price);
    }

}
