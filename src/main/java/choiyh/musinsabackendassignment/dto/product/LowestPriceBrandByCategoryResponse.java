package choiyh.musinsabackendassignment.dto.product;

import choiyh.musinsabackendassignment.util.PriceUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LowestPriceBrandByCategoryResponse {

    private String totalPrice;
    List<ProductDto> products = new ArrayList<>();

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = PriceUtil.priceFormattingWithComma(totalPrice);
    }

}
