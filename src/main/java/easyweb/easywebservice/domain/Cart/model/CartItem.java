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
    /*
    OneToMany에서도 적어줘야해
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    /*
    흠 여기서 근데 Order를 가지는 것도 좀 애매한 것 같다

    왜냐면, 장바구니 아이템은 구매한 다음에 사라져야되는디

    주문한 아이템 정보는 구매한 다음에도 조회를 할 수 있어야하니까

    OrderItem 엔티티가 따로 있어야될 것 같은느낌이.....
     */
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
