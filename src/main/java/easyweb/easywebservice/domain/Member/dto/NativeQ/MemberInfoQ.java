package easyweb.easywebservice.domain.Member.dto.NativeQ;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberInfoQ {
    @Schema(description = "멤버 아이디")
    private Long id;
    @Schema(description = "멤버 본명")
    private String username;
    @Schema(description = "멤버 닉네임")
    private String nickname;
    @Schema(description = "멤버 이메일")
    private String email;

    @Builder
    public MemberInfoQ(Long id, String username, String nickname, String email) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }
}
