package choiyh.musinsabackendassignment.dto.brand;

import choiyh.musinsabackendassignment.dto.product.AddProductRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddBrandRequest {

    private String name;
    private List<AddProductRequest> products;

}
