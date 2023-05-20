package easyweb.easywebservice.domain.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
