package me.weekbelt.apiserver.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String code;

    private String message;

    public static ErrorResponse of(SecurityErrorCode securityErrorCode) {
        return ErrorResponse.builder()
            .code(securityErrorCode.getCode())
            .message(securityErrorCode.getMessage())
            .build();
    }
}
