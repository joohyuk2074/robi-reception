package me.weekbelt.domain.department.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.dto.DepartmentCreateRequest;
import me.weekbelt.persistence.department.dto.DepartmentResponse;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientDepartmentService {

    private final DepartmentDataService departmentDataService;

    @Transactional
    public DepartmentResponse save(DepartmentCreateRequest departmentCreateRequest) {
        Department department = departmentDataService.save(DepartmentMapper.toDepartment(departmentCreateRequest));
        return DepartmentMapper.toDepartmentResponse(department);
    }
}
