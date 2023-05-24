package easyweb.easywebservice.domain.Cart.model;

import easyweb.easywebservice.domain.Order.model.Order;
import easyweb.easywebservice.domain.Product.model.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int count; // 상품 개수

    @Builder
    public CartItem(Product product, Cart cart, int count) {
        this.product = product;
        this.cart = cart;
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
