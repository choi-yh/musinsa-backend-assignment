package choiyh.musinsabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LowestPriceBrandByCategoryResponse {

    private String total;
    List<ProductDto> products = new ArrayList<>();

}
