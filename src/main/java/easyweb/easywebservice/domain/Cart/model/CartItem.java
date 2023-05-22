package easyweb.easywebservice.domain.Cart.model;

import easyweb.easywebservice.domain.Product.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void updateProduct(Product product) {
        this.product = product;
    }

    public void updateCart(Cart cart) {
        this.cart = cart;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
