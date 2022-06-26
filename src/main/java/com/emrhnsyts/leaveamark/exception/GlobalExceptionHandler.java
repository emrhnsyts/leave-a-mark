package com.emrhnsyts.leaveamark.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(Exception exception) {

        return exceptionUtil(exception);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception exception) {

        return exceptionUtil(exception);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<Object> handleLikeNotFoundException(Exception exception) {
        return exceptionUtil(exception);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException(Exception exception) {
        return exceptionUtil(exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Validation failed");
        validationErrorResponse.setCreatedAt(new Date());
        validationErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        validationErrorResponse.setDetails(ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            return objectError.getDefaultMessage();
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(validationErrorResponse, validationErrorResponse.getHttpStatus());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(Exception exception) {
        return exceptionUtilWithCustomMessage("Your username and email must be unique.");
    }


    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException() {
        return exceptionUtilWithCustomMessage("Token is not matched.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException() {
        return exceptionUtilWithCustomMessage("Token is malformed.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException() {
        return exceptionUtilWithCustomMessage("Token is expired.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<Object> handleUnsupportedJwtException() {
        return exceptionUtilWithCustomMessage("Token is not supported.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return exceptionUtilWithCustomMessage("Token is not matched");
    }

    private ResponseEntity<Object> exceptionUtilWithCustomMessage(String message) {
        ErrorResponse errorResponse =
                ErrorResponse.builder().message(message)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .createdAt(new Date()).build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private ResponseEntity<Object> exceptionUtil(Exception exception) {
        return exceptionUtilWithCustomMessage(exception.getMessage());
    }
}
