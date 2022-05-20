package me.weekbelt.persistence.mapper;

import java.util.UUID;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;

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
        return DepartmentResponse.builder()
            .id(department.getId())
            .name(department.getName())
            .number(department.getPhone().getNumber())
            .phoneType(department.getPhone().getPhoneType())
            .branchId(department.getBranchId())
            .build();
    }
}
