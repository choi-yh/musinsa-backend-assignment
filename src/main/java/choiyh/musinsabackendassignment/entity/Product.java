package choiyh.musinsabackendassignment.entity;

import choiyh.musinsabackendassignment.enums.Category;
import choiyh.musinsabackendassignment.enums.CategoryConverter;
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
    @Convert(converter = CategoryConverter.class)
    private Category category;

    // 무신사 특성상 가격 정보에 소수점을 사용하지 않으니 Integer 가 좋을듯함.
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Builder
    public Product(Category category, Integer price, Brand brand) {
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public void mappingBrand(Brand brand) {
        this.brand = brand;
    }

}
