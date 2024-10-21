package choiyh.musinsabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateBrandRequest {

    private String name;
    private List<UpdateProductRequest> products;

}
