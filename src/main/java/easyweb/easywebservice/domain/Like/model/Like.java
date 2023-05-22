package easyweb.easywebservice.domain.Like.model;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Schema(description = "멤버")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Schema(description = "상품")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Builder
    public Like(Member member, Product product) {
        this.member = member;
        this.product = product;
    }
}
