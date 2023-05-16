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
    public static class SignUpDto {
        @Schema(description = "회원가입할 유저 이메일")
        private String email;
        @Schema(description = "회원가입할 유저 비밀번호")
        private String password;
        @Schema(description = "회원가입할 유저 이름")
        private String username;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ResetPasswordDto {
        @Schema(description = "비밀번호 초기화할 이메일", defaultValue = "test@email.com")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class EmailExistenceDto {
        @Schema(description = "존재 확인할 이메일", defaultValue = "test@email.com")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LoginDto {
        @Schema(description = "로그인할 유저 이메일")
        private String email;
        @Schema(description = "로그인할 유저 비밀번호")
        private String password;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LoginResult {
        @Schema(description = "멤버 정보")
        private MemberInfo info;
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberInfo {
        @Schema(description = "멤버 아이디")
        private Long id;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class EmailCertificateDto {
        @Schema(description = "인증할 이메일")
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
