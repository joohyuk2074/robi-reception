package me.weekbelt.apiserver.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DepartmentSynonymResponse {

    private String id;

    private String synonym;
}
