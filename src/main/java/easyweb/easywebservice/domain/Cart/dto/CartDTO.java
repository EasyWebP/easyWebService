package easyweb.easywebservice.domain.Cart.dto;

import java.util.List;

import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "카트 생성 요청 DTO")
    public static class CartCreateDTO {
        @Schema(description = "멤버 아이디")
        private Long memberId;
        @Schema(description = "제품 아이디 리스트")
        private List<Long> productIds;
        @Schema(description = "카트에 담긴 상품 개수")
        private int count;

        public Cart toEntity(MemberRepository memberRepository) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("Member not found"));

            return Cart.builder()
                    .member(member)
                    .count(count)
                    .build();
        }
    }

}