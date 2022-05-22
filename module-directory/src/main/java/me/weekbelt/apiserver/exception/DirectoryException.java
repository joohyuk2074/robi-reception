package me.weekbelt.apiserver.exception;

public class DirectoryException extends BusinessException {

    public DirectoryException(IErrorCode code) {
        super(code);
    }

    public DirectoryException(IErrorCode code, String message) {
        super(code, message);
    }

    public DirectoryException(IErrorCode code, String message, String detail) {
        super(code, message, detail);
    }

}
