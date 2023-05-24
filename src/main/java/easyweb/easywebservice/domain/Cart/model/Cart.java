package easyweb.easywebservice.domain.Cart.model;

import java.util.ArrayList;
import java.util.List;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Order.model.OrderBase;
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

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

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
