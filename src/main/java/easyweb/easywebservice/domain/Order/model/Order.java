package easyweb.easywebservice.domain.Order.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.domain.Cart.model.CartItem;
import easyweb.easywebservice.domain.Member.model.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// make Order entity by referring to the Product, Member, Cart and CartItem entities

@Getter
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private String phoneNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "order")
    private List<CartItem> cartItems;

    @Builder
    public Order(String orderNumber, LocalDate orderDate, String phoneNumber, String address, Member member) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.member = member;
    }

    public void addCartItem(CartItem cartItem) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(cartItem);
        cartItem.setOrder(this);
    }

    public void updateOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void updateOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateAddress(String address) {
        this.address = address;
    }
}
