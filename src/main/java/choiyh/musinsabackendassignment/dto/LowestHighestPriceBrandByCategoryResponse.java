package choiyh.musinsabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowestHighestPriceBrandByCategoryResponse {

    private String category;
    private ProductDto lowestPrice;
    private ProductDto highestPrice;

}
