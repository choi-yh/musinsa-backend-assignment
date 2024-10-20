package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {

    private String category;
    private Integer price; // 프론트에선 데이터 가공을 하지 않는다는 가정하에 "," 로 포맷팅된 가격 정보
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
