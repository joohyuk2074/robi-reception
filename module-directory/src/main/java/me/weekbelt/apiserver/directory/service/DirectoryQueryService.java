package me.weekbelt.apiserver.directory.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.directory.dto.DirectoryResponse;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentTree;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.department.service.DepartmentTreeDataService;
import me.weekbelt.persistence.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DirectoryQueryService {

    private final DepartmentDataService departmentDataService;

    private final DepartmentTreeDataService departmentTreeDataService;

    public DirectoryResponse getDirectory(String ancestor) {
        List<String> subDepartmentIds = getSubDepartmentIds(ancestor);
        List<DepartmentResponse> departmentResponses = getSubDepartmentResponsesByIds(subDepartmentIds);

        return DirectoryResponse.builder()
            .departmentResponses(departmentResponses)
            .build();
    }

    private List<String> getSubDepartmentIds(String ancestor) {
        List<DepartmentTree> departmentTreeList = departmentTreeDataService.getByAncestorAndDepth(ancestor, 1);
        return departmentTreeList.stream()
            .map(DepartmentTree::getDescendant)
            .collect(Collectors.toList());
    }

    private List<DepartmentResponse> getSubDepartmentResponsesByIds(List<String> subDepartmentIds) {
        return subDepartmentIds.stream()
            .map(departmentId -> {
                Department department = departmentDataService.getById(departmentId);
                return DepartmentMapper.toDepartmentResponse(department);
            })
            .collect(Collectors.toList());
    }
}
