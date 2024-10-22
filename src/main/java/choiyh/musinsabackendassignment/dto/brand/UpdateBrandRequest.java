package choiyh.musinsabackendassignment.dto.brand;

import choiyh.musinsabackendassignment.dto.product.UpdateProductRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateBrandRequest {

    private String name;
    private List<UpdateProductRequest> products;

}
