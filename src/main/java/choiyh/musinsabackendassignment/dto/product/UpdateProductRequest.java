package choiyh.musinsabackendassignment.dto.product;

import choiyh.musinsabackendassignment.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

    private Long id; // 해당 dto 를 brand 쪽에서도 사용하기 위해서는 id 필드가 필요..
    private String category;
    private Integer price;

    public Category getCategory() {
        if (category == null) {
            return null;
        }
        return Category.getCategoryFromKorean(category);
    }

}
