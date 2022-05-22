package me.weekbelt.apiserver.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.persistence.PhoneType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUpdateRequest {

    private String name;

    private String number;

    private PhoneType phoneType;
}
