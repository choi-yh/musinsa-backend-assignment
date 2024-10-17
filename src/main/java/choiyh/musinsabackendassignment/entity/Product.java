package choiyh.musinsabackendassignment.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    // 무신사 특성상 가격 정보에 소수점을 사용하지 않으니 Integer 가 좋을듯함.
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
