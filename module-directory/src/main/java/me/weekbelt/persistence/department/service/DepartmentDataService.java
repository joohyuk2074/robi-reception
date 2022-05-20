package me.weekbelt.persistence.department.service;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentDataService {

    private final DepartmentRepository departmentRepository;

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public Department getById(String departmentId) {
        return departmentRepository.findById(departmentId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Department departmentId: " + departmentId));
    }
}
