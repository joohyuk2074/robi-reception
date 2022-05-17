package me.weekbelt.domain.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentTree;
import me.weekbelt.persistence.department.dto.DepartmentCreateRequest;
import me.weekbelt.persistence.department.dto.DepartmentResponse;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.department.service.DepartmentTreeDataService;
import me.weekbelt.persistence.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientDepartmentService {

    private final DepartmentDataService departmentDataService;

    private final DepartmentTreeDataService departmentTreeDataService;

    @Transactional
    public DepartmentResponse save(DepartmentCreateRequest departmentCreateRequest) {
        Department department = departmentDataService.save(DepartmentMapper.toDepartment(departmentCreateRequest));

        List<DepartmentTree> departmentTrees = createDepartmentTrees(departmentCreateRequest, department);
        departmentTreeDataService.saveAll(departmentTrees);

        return DepartmentMapper.toDepartmentResponse(department);
    }

    private List<DepartmentTree> createDepartmentTrees(DepartmentCreateRequest departmentCreateRequest, Department department) {
        String parentDepartment = departmentCreateRequest.getParentId();
        List<DepartmentTree> parentDepartmentTrees = departmentTreeDataService.getByDescendant(parentDepartment);

        List<DepartmentTree> newDepartmentTrees = parentDepartmentTrees.stream()
            .map(departmentTree -> DepartmentTree.builder()
                .id(UUID.randomUUID().toString())
                .ancestor(departmentTree.getAncestor())
                .descendant(department.getId())
                .depth(departmentTree.getDepth() + 1)
                .build())
            .toList();
        List<DepartmentTree> departmentTrees = new ArrayList<>(newDepartmentTrees);

        DepartmentTree departmentTree = DepartmentTree.builder()
            .id(UUID.randomUUID().toString())
            .ancestor(department.getId())
            .descendant(department.getId())
            .depth(0)
            .build();
        departmentTrees.add(departmentTree);

        return departmentTrees;
    }
}
