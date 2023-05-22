package easyweb.easywebservice.domain.Like.exception.handler;

import easyweb.easywebservice.domain.Like.exception.LikeAlreadyExists;
import easyweb.easywebservice.domain.Like.exception.LikeDoesntExist;
import easyweb.easywebservice.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class LikeExceptionHandler {

    @ExceptionHandler(LikeAlreadyExists.class)
    protected final ResponseEntity<ErrorResponse> handleLikeAlreadyExists(
            LikeAlreadyExists ex, WebRequest request
    ) {

        log.info("좋아요가 이미 존재");
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).errorCode(409L).build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LikeDoesntExist.class)
    protected final ResponseEntity<ErrorResponse> handleLikeDoesntExists(
            LikeDoesntExist ex, WebRequest request
    ) {
        log.info("좋아요가 존재하지 않는다");
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).errorCode(409L).build(), HttpStatus.CONFLICT);
    }
}
