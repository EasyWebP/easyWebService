package easyweb.easywebservice.domain.Member.exception.handler;

import easyweb.easywebservice.domain.Member.exception.EmailCertificationExpireException;
import easyweb.easywebservice.domain.Member.exception.MemberExistsWithEmailException;
import easyweb.easywebservice.domain.Member.exception.MemberNotFoundByEmail;
import easyweb.easywebservice.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MemberNotFoundByEmail.class)
    protected final ResponseEntity<ErrorResponse> handleMemberNotFoundByEmail(
            MemberNotFoundByEmail ex, WebRequest request
    ) {
        log.info("이메일로 존재하는 유저 발견 X");
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(404L)
                        .message(ex.getMessage()).build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EmailCertificationExpireException.class)
    protected final ResponseEntity<ErrorResponse> handleEmailCertificationExpireException(
            EmailCertificationExpireException ex, WebRequest request
    ) {
        log.info("이메일 인증 코드 만료");
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorCode(409L).build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MemberExistsWithEmailException.class)
    protected final ResponseEntity<ErrorResponse> handleMemberExistsWithEmailException(
            MemberExistsWithEmailException ex, WebRequest request
    ) {
        log.info("이메일로 멤버가 존재");
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorCode(409L)
                        .build(), HttpStatus.CONFLICT
        );
    }
}
