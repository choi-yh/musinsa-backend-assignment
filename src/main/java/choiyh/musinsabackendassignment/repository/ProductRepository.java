package choiyh.musinsabackendassignment.repository;

import choiyh.musinsabackendassignment.entity.Product;
import choiyh.musinsabackendassignment.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand WHERE p.category = :category")
    Page<Product> findByCategory(@Param("category") Category category, Pageable pageable);
}
