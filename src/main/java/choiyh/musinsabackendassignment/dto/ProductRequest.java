package choiyh.musinsabackendassignment.dto;

import choiyh.musinsabackendassignment.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String category; // TODO: enum 변경 가능
    private Integer price; // 프론트에선 데이터 가공을 하지 않는다는 가정하에 "," 로 포맷팅된 가격 정보

    public static Product toEntity(ProductRequest request) {
        return Product.builder()
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }

}