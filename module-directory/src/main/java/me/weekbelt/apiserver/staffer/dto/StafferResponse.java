package me.weekbelt.apiserver.staffer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StafferResponse {

    private String department;

    private String name;

    private String number;

    private String phoneType;

    private String branchId;
}
