package me.weekbelt.persistence.department.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.persistence.PhoneType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    @NotNull
    private PhoneType phoneType;

    private String parentId;

    @NotBlank
    private String branchId;
}
