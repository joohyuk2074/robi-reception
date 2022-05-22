package me.weekbelt.apiserver.department.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.persistence.PhoneType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse {

    private String id;

    private String name;

    private String number;

    private PhoneType phoneType;

    private String branchId;

    private List<String> synonyms;
}
