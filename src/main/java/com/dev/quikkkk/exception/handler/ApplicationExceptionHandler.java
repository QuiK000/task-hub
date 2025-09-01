package com.dev.quikkkk.exception.handler;

import com.dev.quikkkk.dto.response.ErrorResponse;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.dev.quikkkk.exception.ErrorCode.BAD_CREDENTIALS;
import static com.dev.quikkkk.exception.ErrorCode.ERR_USER_DISABLED;
import static com.dev.quikkkk.exception.ErrorCode.USERNAME_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse body = ErrorResponse
                .builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .build();

        log.info("Business Exception: {}", ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return ResponseEntity.status(
                ex.getErrorCode().getStatus() != null
                        ? ex.getErrorCode().getStatus()
                        : BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException ex) {
        log.debug(ex.getMessage(), ex);

        ErrorResponse body = ErrorResponse
                .builder()
                .code(ERR_USER_DISABLED.getCode())
                .message(ERR_USER_DISABLED.getDefaultMessage())
                .build();

        return ResponseEntity.status(ERR_USER_DISABLED.getStatus()).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex) {
        log.debug(ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse
                .builder()
                .message(BAD_CREDENTIALS.getDefaultMessage())
                .build();

        return ResponseEntity.status(BAD_CREDENTIALS.getStatus()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException ex) {
        log.debug(ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse
                .builder()
                .code(USERNAME_NOT_FOUND.getCode())
                .message(USERNAME_NOT_FOUND.getDefaultMessage())
                .build();

        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AuthorizationDeniedException ex) {
        log.debug(ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse
                .builder()
                .message("You are not authorized to perform this operation")
                .build();

        return new ResponseEntity<>(response, FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException ex) {
        log.debug(ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse
                .builder()
                .code("TBD")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ValidationError> errors = new ArrayList<>();

        ex
                .getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorCode = error.getDefaultMessage();

                    errors.add(ErrorResponse.ValidationError
                            .builder()
                            .field(fieldName)
                            .code(errorCode)
                            .message(errorCode)
                            .build());
                });

        ErrorResponse response = ErrorResponse
                .builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse response = ErrorResponse
                .builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getDefaultMessage())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }
}
