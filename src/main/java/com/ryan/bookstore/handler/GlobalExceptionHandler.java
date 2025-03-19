package com.ryan.bookstore.handler;

import com.ryan.bookstore.bean.ResponseBody;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.sasl.AuthenticationException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 400 ERROR
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("잘못된 입력 값이 있습니다.");

        ResponseBody errorMessage = ResponseBody.builder()
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest()
                .body(errorMessage);
    }

    /**
     * 400 ERROR
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseBody> handleBadRequestException(BadRequestException ex) {
        ResponseBody errorMessage = ResponseBody.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest()
                .body(errorMessage);
    }

    /**
     * 401 ERROR
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseBody> handleAuthenticationException(AuthenticationException ex) {
        ResponseBody errorMessage = ResponseBody.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorMessage);
    }

    /**
     * 500 ERROR
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseBody> handleIllegalStateException(IllegalStateException ex) {
        ResponseBody errorMessage = ResponseBody.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return ResponseEntity.internalServerError()
                .body(errorMessage);
    }

    /**
     * 500 ERROR
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody> handleException(Exception ex) {
        ResponseBody errorMessage = ResponseBody.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return ResponseEntity.internalServerError()
                .body(errorMessage);
    }
}