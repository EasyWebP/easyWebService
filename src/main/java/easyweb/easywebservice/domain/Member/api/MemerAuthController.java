package easyweb.easywebservice.domain.Member.api;

import easyweb.easywebservice.domain.Member.application.AuthService;
import easyweb.easywebservice.domain.Member.application.EmailCertificationService;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.EmailExistenceDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginResult;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.SignUpDto;
import easyweb.easywebservice.domain.common.dto.CommonDto.BooleanApiResult;
import easyweb.easywebservice.domain.common.dto.CommonDto.StringApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 인증", description = "사용자 인증 관련 API 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemerAuthController {
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
    @PostMapping(value = "/email/exists")
    public ResponseEntity<BooleanApiResult> checkEmailExistence(@RequestBody EmailExistenceDto emailExistenceDto) {
        return ResponseEntity.ok(authService.checkEmailExistence(emailExistenceDto));
    }

    @Operation(summary = "로그인", description = "로그인 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공시", content = @Content(schema = @Schema(implementation = LoginResult.class)))
    })
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
