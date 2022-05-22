package me.weekbelt.apiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DirectoryErrorCode implements IErrorCode {

    CANNOT_CHANGE_PARENT_DEPARTMENT(HttpStatus.BAD_REQUEST, "directory-001", "Cannot change the parent department");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;
}
