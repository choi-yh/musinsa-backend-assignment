package choiyh.musinsabackendassignment.repository;

import choiyh.musinsabackendassignment.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT b FROM Brand b JOIN FETCH b.products")
    Page<Brand> findAllWithProducts(Pageable pageable);
}
