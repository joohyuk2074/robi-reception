package me.weekbelt.apiserver.directory.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.directory.dto.DirectoryResponse;
import me.weekbelt.apiserver.directory.service.DirectoryQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class DirectoryController {

    private final DirectoryQueryService directoryQueryService;

    @GetMapping("/v1/directories/{departmentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DirectoryResponse getDepartments(@PathVariable(required = false) String departmentId) {
        return directoryQueryService.getDirectory(departmentId);
    }
}
