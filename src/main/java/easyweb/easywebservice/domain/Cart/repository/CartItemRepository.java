package easyweb.easywebservice.domain.Cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
