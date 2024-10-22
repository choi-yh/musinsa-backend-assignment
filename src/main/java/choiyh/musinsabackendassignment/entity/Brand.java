package choiyh.musinsabackendassignment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Product> products;

    @Builder
    public Brand(String name, List<Product> products) {
        this.name = name;
        this.products = products;

        for (Product product : products) {
            product.mappingBrand(this);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }

}
