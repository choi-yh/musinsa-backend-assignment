package choiyh.musinsabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LowestPriceByBrand {

    private String total;
    private String brand;
    private List<ProductDto> products;

}
