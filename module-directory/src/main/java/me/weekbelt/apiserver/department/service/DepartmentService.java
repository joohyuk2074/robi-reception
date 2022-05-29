package me.weekbelt.apiserver.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.department.dto.DepartmentUpdateRequest;
import me.weekbelt.apiserver.exception.DirectoryErrorCode;
import me.weekbelt.apiserver.exception.DirectoryException;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentSynonym;
import me.weekbelt.persistence.department.DepartmentTree;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.department.service.DepartmentSynonymDataService;
import me.weekbelt.persistence.department.service.DepartmentTreeDataService;
import me.weekbelt.persistence.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentDataService departmentDataService;

    private final DepartmentTreeDataService departmentTreeDataService;

    private final DepartmentSynonymDataService departmentSynonymDataService;

    @Transactional
    public DepartmentResponse save(DepartmentCreateRequest departmentCreateRequest) {
        Department department = departmentDataService.save(DepartmentMapper.toDepartment(departmentCreateRequest));

        List<DepartmentTree> departmentTrees = createDepartmentTrees(departmentCreateRequest, department);
        departmentTreeDataService.saveAll(departmentTrees);

        return DepartmentMapper.toDepartmentResponse(department);
    }

    private List<DepartmentTree> createDepartmentTrees(DepartmentCreateRequest departmentCreateRequest, Department department) {
        String parentDepartment = departmentCreateRequest.getParentId();

        List<DepartmentTree> departmentTrees = new ArrayList<>();
        if (parentDepartment != null) {
            List<DepartmentTree> parentDepartmentTrees = departmentTreeDataService.getByDescendant(parentDepartment);

            List<DepartmentTree> newDepartmentTrees = parentDepartmentTrees.stream()
                .map(departmentTree -> DepartmentTree.builder()
                    .id(UUID.randomUUID().toString())
                    .ancestor(departmentTree.getAncestor())
                    .descendant(department.getId())
                    .depth(departmentTree.getDepth() + 1)
                    .branchId(department.getBranchId())
                    .build())
                .toList();
            departmentTrees.addAll(newDepartmentTrees);
        }

        DepartmentTree departmentTree = DepartmentTree.builder()
            .id(UUID.randomUUID().toString())
            .ancestor(department.getId())
            .descendant(department.getId())
            .depth(0)
            .branchId(department.getBranchId())
            .build();
        departmentTrees.add(departmentTree);

        return departmentTrees;
    }

    @Transactional
    public DepartmentResponse update(String departmentId, DepartmentUpdateRequest updateRequest) {
        Department department = departmentDataService.getById(departmentId);
        department.update(updateRequest);
        return DepartmentMapper.toDepartmentResponse(department);
    }

    @Transactional
    public void delete(String departmentId) {
        if (!deletable(departmentId)) {
            throw new DirectoryException(DirectoryErrorCode.CANNOT_DELETE_DEPARTMENT, "Sub-departments or employees exist");
        }

        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByDescendant(departmentId);
        departmentTreeDataService.deleteAll(departmentTrees);

        Department department = departmentDataService.getById(departmentId);
        departmentDataService.delete(department);
    }

    private boolean deletable(String departmentId) {
        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByAncestor(departmentId);
        return departmentTrees.stream()
            .noneMatch(departmentTree -> departmentTree.getAncestor().equals(departmentId) && departmentTree.getDepth() != 0);
    }

    @Transactional
    public DepartmentResponse addSynonyms(String departmentId, List<String> synonyms) {
        Department department = departmentDataService.getById(departmentId);
        department.addSynonyms(synonyms);
        return DepartmentMapper.toDepartmentResponse(department);
    }

    @Transactional
    public DepartmentResponse deleteSynonym(String departmentId, String synonymId) {
        Department department = departmentDataService.getById(departmentId);
        DepartmentSynonym departmentSynonym = departmentSynonymDataService.getById(synonymId);
        department.removeSynonym(departmentSynonym);
        return DepartmentMapper.toDepartmentResponse(department);
    }
}
