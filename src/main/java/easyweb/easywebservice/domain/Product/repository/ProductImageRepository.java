package easyweb.easywebservice.domain.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Product.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    ProductImage findByImagePath(String imagePath);

}
