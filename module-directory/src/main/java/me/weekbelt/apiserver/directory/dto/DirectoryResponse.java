package me.weekbelt.apiserver.directory.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryResponse {

    private List<DepartmentResponse> departmentResponses;
}
