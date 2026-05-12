package com.startup.domain.example.error;

import com.startup.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExampleErrorCode implements ErrorCode {
    EXAMPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "Example not found."),
    DUPLICATED_EXAMPLE_TITLE(HttpStatus.CONFLICT, "E002", "Example title already exists."),
    INVALID_EXAMPLE_STATUS(HttpStatus.BAD_REQUEST, "E003", "Invalid example status.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
