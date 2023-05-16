package easyweb.easywebservice.domain.Member.api;

import easyweb.easywebservice.domain.Member.application.AuthService;
import easyweb.easywebservice.domain.Member.application.EmailCertificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("")
    private ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
