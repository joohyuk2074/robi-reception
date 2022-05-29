package me.weekbelt.apiserver.department.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.department.dto.DepartmentUpdateRequest;
import me.weekbelt.apiserver.department.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/v1/departments")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartmentResponse save(@RequestBody @Valid DepartmentCreateRequest departmentCreateRequest) {
        return departmentService.save(departmentCreateRequest);
    }

    @PatchMapping("/v1/departments/{departmentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DepartmentResponse update(@RequestBody @Valid DepartmentUpdateRequest departmentUpdateRequest, @PathVariable String departmentId) {
        return departmentService.update(departmentId, departmentUpdateRequest);
    }

    @DeleteMapping("/v1/departments/{departmentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable String departmentId) {
        departmentService.delete(departmentId);
    }

    @PostMapping("/v1/departments/{departmentId}/synonyms")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartmentResponse addSynonyms(@PathVariable String departmentId, List<String> synonyms) {
        return departmentService.addSynonyms(departmentId, synonyms);
    }

    @DeleteMapping("/v1/departments/{departmentId}/synonyms/{synonymId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DepartmentResponse deleteSynonym(@PathVariable String departmentId, @PathVariable String synonymId) {
        return departmentService.deleteSynonym(departmentId, synonymId);
    }
}
