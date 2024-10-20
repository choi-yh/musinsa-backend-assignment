package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

    private String category;
    private Integer price;

    public Category getCategory() {
        return Category.getCategoryFromKorean(category);
    }

}
