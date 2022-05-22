package me.weekbelt.apiserver.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final IErrorCode code;

    private final String detail;

    public BusinessException(IErrorCode code) {
        this(code, code.getMessage(), null);
    }

    public BusinessException(IErrorCode code, String message) {
        this(code, message, null);
    }

    public BusinessException(IErrorCode code, String message, String detail) {
        super(message);
        this.code = code;
        this.detail = detail;
    }
}
