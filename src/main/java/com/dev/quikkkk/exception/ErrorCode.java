package com.dev.quikkkk.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id %s", NOT_FOUND),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already exists", BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "Username already exists", BAD_REQUEST),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "Username not found", NOT_FOUND),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED("ACCESS_DENIED", "Access denied", FORBIDDEN),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Bad credentials", FORBIDDEN),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password mismatch", FORBIDDEN),
    ERR_USER_DISABLED("ERR_USER_DISABLED", "User disabled", FORBIDDEN),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "Invalid current password", BAD_REQUEST),
    INVALID_TOKEN("INVALID_TOKEN", "Invalid token", BAD_REQUEST),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "Token expired", BAD_REQUEST),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", "Invalid refresh token", BAD_REQUEST),
    INVALID_ROLE("INVALID_ROLE", "Invalid role", BAD_REQUEST),
    INVALID_PAGE_NUMBER("INVALID_PAGE_NUMBER", "Invalid page number", BAD_REQUEST),
    INVALID_PAGE_SIZE("INVALID_PAGE_SIZE", "Invalid page size", BAD_REQUEST),
    INVALID_SORT_FIELD("INVALID_SORT_FIELD", "Invalid sort field", BAD_REQUEST),
    INVALID_SORT_DIRECTION("INVALID_SORT_DIRECTION", "Invalid sort direction", BAD_REQUEST),
    INVALID_SEARCH_VALUE("INVALID_SEARCH_VALUE", "Invalid search value", BAD_REQUEST),
    INVALID_SEARCH_FIELD("INVALID_SEARCH_FIELD", "Invalid search field", BAD_REQUEST),
    INVALID_SEARCH_OPERATION("INVALID_SEARCH_OPERATION", "Invalid search operation", BAD_REQUEST),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Change password mismatch", BAD_REQUEST),
    ;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
