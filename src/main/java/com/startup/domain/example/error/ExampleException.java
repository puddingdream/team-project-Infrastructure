package com.startup.domain.example.error;

import com.startup.common.error.BusinessException;

public class ExampleException extends BusinessException {

    public ExampleException(ExampleErrorCode errorCode) {
        super(errorCode);
    }

    public ExampleException(ExampleErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
