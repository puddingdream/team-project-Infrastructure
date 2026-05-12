package com.startup.infrastructure.redis.lock;

import com.startup.common.error.BusinessException;
import com.startup.common.error.CommonErrorCode;

public class LockException extends BusinessException {

    public LockException(String message) {
        super(CommonErrorCode.CONFLICT, message);
    }

    public LockException(String message, Throwable cause) {
        super(CommonErrorCode.CONFLICT, message, cause);
    }
}
