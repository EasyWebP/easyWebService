package easyweb.easywebservice.domain.Cart.model;

import java.util.ArrayList;
import java.util.List;

import easyweb.easywebservice.domain.Member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
엔티티 설계는 굳 입니당
다만 count는 따로 구할 수 있는 방법 있으니 필요 없을 것 같고,
updateMember도 유지보수성을 위해 빼는게 좋을듯?

updateMember있으면 저 메서드 잘못 쓰면 내가 담아놓은 장바구니가 너의 장바구니가 될 수도 있는데

이런 일은 일어나면 안되겠지???
 */

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

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    private int count = 0; // 카트에 담긴 상품 개수

    @Builder
    public Cart(Member member, int count) {
        this.member = member;
        this.count = count;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
