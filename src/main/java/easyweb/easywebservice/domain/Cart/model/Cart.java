package easyweb.easywebservice.domain.Cart.model;

import java.util.ArrayList;
import java.util.List;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Order.model.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    /*
    FetchType.LAZY 적어줭
     */
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();
    /*
    주문이랑 카트랑 관계 가지는 좀 애매하네

    사실 없어도 될듯??

    주문 정보에서 어떤 장바구니에서 주문한건지에 대한 정보가 딱히 필요 없을 것 같아

    관계를 가져도 카트는 한 멤버당 하나니까, @OneToMany로 관계를 맺어야될 것 같은 느낌
     */
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int count = 0; // 카트에 담긴 상품 개수

    @Builder
    public Cart(Member member, int count) {
        this.member = member;
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
