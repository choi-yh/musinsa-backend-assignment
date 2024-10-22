package choiyh.musinsabackendassignment.dto.product;

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
    private String brandName;
    private String price;

    public static ProductDto of(Product product) {
        return ProductDto.builder()
                .category(product.getCategory().toString())
                .brandName(product.getBrand().getName())
                .price(PriceUtil.priceFormattingWithComma(product.getPrice()))
                .build();
    }

    public static ProductDto ofWithoutCategory(Product product) {
        return ProductDto.builder()
                .brandName(product.getBrand().getName())
                .price(PriceUtil.priceFormattingWithComma(product.getPrice()))
                .build();
    }

    public static ProductDto ofWithoutBrand(Product product) {
        return ProductDto.builder()
                .category(product.getCategory().toString())
                .price(PriceUtil.priceFormattingWithComma(product.getPrice()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(brandName, that.brandName) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, brandName, price);
    }

}
