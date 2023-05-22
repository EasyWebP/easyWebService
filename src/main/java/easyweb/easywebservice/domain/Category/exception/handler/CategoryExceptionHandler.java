package easyweb.easywebservice.domain.Category.exception.handler;

import easyweb.easywebservice.domain.Category.exception.CategoryNotFoundException;
import easyweb.easywebservice.domain.Category.exception.DuplicateCategoryException;
import easyweb.easywebservice.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class CategoryExceptionHandler {
    @ExceptionHandler(DuplicateCategoryException.class)
    protected final ResponseEntity<ErrorResponse> handleDuplicateCategoryException(
            DuplicateCategoryException ex, WebRequest request
    ) {

        log.info("카테고리 중복이 발생하였습니다");
        return new ResponseEntity<>(ErrorResponse.builder().errorCode(409L).message(ex.getMessage()).build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected final ResponseEntity<ErrorResponse> handleCategoryNotFoundException(
            CategoryNotFoundException ex, WebRequest request
    ) {

        log.info("카테고리를 찾지 못했습니다");
        return new ResponseEntity<>(ErrorResponse.builder().errorCode(404L).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }
}
