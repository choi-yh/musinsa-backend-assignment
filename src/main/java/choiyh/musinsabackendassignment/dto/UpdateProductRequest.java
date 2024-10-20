package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

    private String category;
    private Integer price; // 프론트에선 데이터 가공을 하지 않는다는 가정하에 "," 로 포맷팅된 가격 정보

    public Category getCategory() {
        return Category.getCategoryFromKorean(category);
    }

}
