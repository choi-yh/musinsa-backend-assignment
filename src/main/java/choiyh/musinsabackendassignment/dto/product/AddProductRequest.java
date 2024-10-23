package choiyh.musinsabackendassignment.dto.product;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {

    private String category;
    private Integer price;

    @Schema(name = "brand_id")
    private Long brandId;

    public static Product toEntity(AddProductRequest request) {
        return Product.builder()
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }

    public Category getCategory() {
        return Category.getCategoryFromKorean(category);
    }

}
