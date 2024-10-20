package choiyh.musinsabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrandRequest {

    private String name;
    private List<AddProductRequest> products;

}
