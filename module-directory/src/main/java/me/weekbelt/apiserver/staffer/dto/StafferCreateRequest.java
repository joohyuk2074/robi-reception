package me.weekbelt.apiserver.staffer.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StafferCreateRequest {

    @NotBlank
    private String departmentId;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    @NotNull
    private String phoneType;

    @NotBlank
    private String branchId;
}
