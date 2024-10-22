package choiyh.musinsabackendassignment.dto.brand;

import choiyh.musinsabackendassignment.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LowestPriceByBrand {

    private String totalPrice;
    private String brand;
    private List<ProductDto> products;

}
