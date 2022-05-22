package me.weekbelt.persistence.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.department.dto.DepartmentSynonymResponse;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentSynonym;
import org.springframework.util.ObjectUtils;

public class DepartmentMapper {

    public static Department toDepartment(DepartmentCreateRequest departmentCreateRequest) {
        Phone phone = Phone.builder()
            .number(departmentCreateRequest.getNumber())
            .phoneType(departmentCreateRequest.getPhoneType())
            .build();

        return Department.builder()
            .id(UUID.randomUUID().toString())
            .name(departmentCreateRequest.getName())
            .phone(phone)
            .branchId(departmentCreateRequest.getBranchId())
            .build();
    }

    public static DepartmentResponse toDepartmentResponse(Department department) {
        List<DepartmentSynonymResponse> synonyms = new ArrayList<>();
        if (!ObjectUtils.isEmpty(department.getDepartmentSynonyms())) {
            synonyms = department.getDepartmentSynonyms().stream()
                .map(DepartmentMapper::toDepartmentSynonymResponse)
                .toList();
        }

        return DepartmentResponse.builder()
            .id(department.getId())
            .name(department.getName())
            .number(department.getPhone().getNumber())
            .phoneType(department.getPhone().getPhoneType())
            .branchId(department.getBranchId())
            .synonyms(synonyms)
            .build();
    }

    private static DepartmentSynonymResponse toDepartmentSynonymResponse(DepartmentSynonym departmentSynonym) {
        return DepartmentSynonymResponse.builder()
            .id(departmentSynonym.getId())
            .synonym(departmentSynonym.getSynonym())
            .build();
    }
}
