package easyweb.easywebservice.domain.Cart.model;

import easyweb.easywebservice.domain.Product.model.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
여기서도 updateProduct랑 updateCart는 빼는게 좋을듯??

유지보수성을 위해서..

저런 메서드 만들어 놓고 잘못 쓰면 갑자기 내 장바구니 상품이 다른 사람 장바구니에 담기게되고 그럴 수도 있어

updateCount는 필요할 것 같네
 */

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
}
