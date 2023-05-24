package easyweb.easywebservice.domain.Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import easyweb.easywebservice.domain.Order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
