package easyweb.easywebservice.domain.Cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
