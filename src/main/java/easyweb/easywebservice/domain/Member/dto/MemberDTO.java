package easyweb.easywebservice.domain.Member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class MemberDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ResetPasswordRequest {
        @Schema(description = "비밀번호 초기화할 이메일", defaultValue = "test@email.com")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CodeConfirmDto {
        @Schema(description = "일치시", example = "true", defaultValue = "false")
        private boolean matches;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class EmailConfirmCodeDto {
        @NotNull
        @Email
        private String email;
        @NotNull
        @Size(min = 6)
        private String code;
    }

}
