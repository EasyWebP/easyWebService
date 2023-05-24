package easyweb.easywebservice.domain.Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Order.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
