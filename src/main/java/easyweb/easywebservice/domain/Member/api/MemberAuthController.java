package easyweb.easywebservice.domain.Member.api;

import easyweb.easywebservice.domain.Member.application.AuthService;
import easyweb.easywebservice.domain.Member.application.EmailCertificationService;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.*;
import easyweb.easywebservice.domain.Token.dto.TokenDTO;
import easyweb.easywebservice.domain.Token.dto.TokenDTO.ReissueRequest;
import easyweb.easywebservice.domain.Token.dto.TokenDTO.TokenIssueDTO;
import easyweb.easywebservice.domain.common.dto.CommonDto.BooleanApiResult;
import easyweb.easywebservice.domain.common.dto.CommonDto.StringApiResult;
import easyweb.easywebservice.global.error.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 인증", description = "사용자 인증 관련 API 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberAuthController {
    private final AuthService authService;
    private final EmailCertificationService emailCertificationService;

    @Operation(summary = "멤버 회원가입", description = "멤버 회원가입 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 회원가입 성공시", content = @Content(schema = @Schema(implementation = StringApiResult.class)))
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<StringApiResult> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @Operation(summary = "멤버 이메일 중복 확인", description = "멤버 이메일 중복 확인 API입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 이메일 중복 조회시", content = @Content(schema = @Schema(implementation = BooleanApiResult.class)))
    })
    @GetMapping(value = "/email/exists/{email}")
    public ResponseEntity<BooleanApiResult> checkEmailExistence(@PathVariable String email) {
        return ResponseEntity.ok(authService.checkEmailExistence(email));
    }

    @Operation(summary = "멤버 닉네임 중복 확인", description = "멤버 닉네임 중복 확인 API입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 닉네임 중복 조회시", content = @Content(schema = @Schema(implementation = BooleanApiResult.class)))
    })
    @GetMapping(value = "/nickname/exists/{nickname}")
    public ResponseEntity<BooleanApiResult> checkNicknameExistence(@PathVariable String nickname) {
        return ResponseEntity.ok(authService.checkNicknameExistence(nickname));
    }

    @Operation(summary = "로그인", description = "로그인 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공시", content = @Content(schema = @Schema(implementation = LoginResult.class)))
    })
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @Operation(summary = "멤버 이메일 인증", description = "멤버 이메일 인증시 호출하는 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 이메일 인증 요청시", content = @Content(schema = @Schema(implementation = StringApiResult.class)))
    })
    @PostMapping(value = "/email/certificate")
    public ResponseEntity<StringApiResult> memberEmailCertificate(@RequestBody EmailCertificateDto emailCertificateDto) throws Exception {

        return ResponseEntity.ok(emailCertificationService.sendSimpleMessage(emailCertificateDto.getEmail()));
    }

    @Operation(summary = "멤버 이메일 인증 확인", description = "멤버 이메일 인증 확인시 호출하는 API입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 이메일 인증 확인시", content = @Content(schema = @Schema(implementation = CodeConfirmDto.class)))
    })
    @PostMapping(value = "/email/check")
    public ResponseEntity<CodeConfirmDto> memberEmailCertificateCheck(@RequestBody EmailConfirmCodeDto emailConfirmCodeDto) {

        return ResponseEntity.ok(emailCertificationService.confirmCode(emailConfirmCodeDto));
    }

    @Operation(summary = "토큰 재발급 요청 API", description = "토큰 재발급시 호출하는 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenIssueDTO.class))),
            @ApiResponse(responseCode = "401", description = "토큰 재발급 실패, 만료된 리프레쉬", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/reissue")
    public ResponseEntity<TokenIssueDTO> reissue(@RequestBody ReissueRequest reissueRequest) {

        return ResponseEntity.ok(authService.reissue(reissueRequest));
    }

    @Operation(summary = "로그아웃시 리다이렉트 API", description = "로그아웃시 호출되는 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @GetMapping(value = "/logout-redirect")
    public ResponseEntity<StringApiResult> logoutRedirect() {
        return ResponseEntity.ok(new StringApiResult("LOGOUT"));
    }
}
