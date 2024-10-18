package choiyh.musinsabackendassignment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category; // TODO: 테이블 따로 분리해도 좋을듯함. values 를 따로 enum 으로 관리

    // 무신사 특성상 가격 정보에 소수점을 사용하지 않으니 Integer 가 좋을듯함.
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Builder
    public Product(String category, Integer price, Brand brand) {
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public void mappingBrand(Brand brand) {
        this.brand = brand;
    }

}
