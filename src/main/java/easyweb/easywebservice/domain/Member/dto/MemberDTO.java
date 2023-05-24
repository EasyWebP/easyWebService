package easyweb.easywebservice.domain.Member.dto;

import easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ;
import easyweb.easywebservice.domain.Member.model.Authority;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.model.UserLoginType;
import easyweb.easywebservice.domain.Token.dto.TokenDTO;
import easyweb.easywebservice.domain.Token.dto.TokenDTO.TokenIssueDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static easyweb.easywebservice.domain.Member.model.Authority.ROLE_USER;
import static easyweb.easywebservice.domain.Member.model.UserLoginType.EMAIL;

public class MemberDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "회원가입 요청")
    public static class SignUpDto {
        @Schema(description = "회원가입할 유저 이메일")
        @Email
        private String email;
        @Schema(description = "회원가입할 유저 비밀번호")
        @NotNull
        @Size(min=8)
        private String password;
        @Schema(description = "회원가입할 유저 이름")
        @NotNull
        private String username;
        @Schema(description = "회원가입할 유저 닉네임")
        @NotNull
        private String nickname;

        public void encode(PasswordEncoder passwordEncoder) {
            this.password = passwordEncoder.encode(this.password);
        }

        public Member toEntity() {
            return Member.builder()
                    .nickName(nickname)
                    .userName(username)
                    .authority(ROLE_USER)
                    .deleted(false)
                    .email(email)
                    .password(password)
                    .userLoginType(EMAIL)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "비밀번호 초기화 요청")
    public static class ResetPasswordDto {
        @Schema(description = "비밀번호 초기화할 이메일", defaultValue = "test@email.com")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "이메일 중복 확인")
    public static class EmailExistenceDto {
        @Schema(description = "존재 확인할 이메일", defaultValue = "test@email.com")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "로그인 요청")
    public static class LoginDto {
        @Schema(description = "로그인할 유저 이메일")
        private String email;
        @Schema(description = "로그인할 유저 비밀번호")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "소셜 로그인시 회원가입 요청")
    public static class SocialLoginDto {
        @Schema(description = "로그인할 유저 이메일")
        private String email;
        @Schema(description = "유저 본명")
        private String username;
        @Schema(description = "유저 닉네임")
        private String nickname;
        @Schema(description = "유저 로그인 타입")
        private UserLoginType loginType;

        public Member toEntity(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .password(passwordEncoder.encode(username))
                    .deleted(false)
                    .email(email)
                    .userName(username)
                    .authority(ROLE_USER)
                    .nickName(nickname)
                    .userLoginType(loginType).build();
        }

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, "12345678");
        }
    }



    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "로그인 결과")
    public static class LoginResult {
        @Schema(description = "멤버 정보")
        private MemberInfoQ memberInfo;
        @Schema(description = "토큰 정보")
        private TokenIssueDTO tokenInfo;
    }



    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "이메일 인증 요청")
    public static class EmailCertificateDto {
        @Schema(description = "인증할 이메일")
        private String email;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(description = "이메일 인증 코드 일치 여부")
    public static class CodeConfirmDto {
        @Schema(description = "일치시", example = "true", defaultValue = "false")
        private boolean matches;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "이메일 인증 코드 확인 요청")
    public static class EmailConfirmCodeDto {
        @NotNull
        @Email
        private String email;
        @NotNull
        @Size(min = 6)
        private String code;
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "이메일 인증 코드 확인 요청")
    public static class MemberUpdateDto {
        private String nickname;
    }
}
