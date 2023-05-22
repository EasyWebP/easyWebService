package easyweb.easywebservice.domain.Product.repository;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Product.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new easyweb.easywebservice.domain.Product.dto.ProductInfoDto(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.manufacturer, " +
            "p.imagePath) " +
            "FROM Product p " +
            "JOIN Liked l ON l.member.id = :id")
    Page<ProductInfoDto> findLikedProducts(@Param("id") Long memberId, Pageable pageable);
}
