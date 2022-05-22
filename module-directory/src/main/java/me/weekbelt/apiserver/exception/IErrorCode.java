package me.weekbelt.apiserver.exception;

import org.springframework.http.HttpStatus;

public interface IErrorCode {

    String getCode();

    HttpStatus getHttpStatus();

    String getMessage();
}
