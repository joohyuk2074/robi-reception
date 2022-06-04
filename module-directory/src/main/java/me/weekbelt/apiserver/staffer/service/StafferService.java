package me.weekbelt.apiserver.staffer.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.staffer.dto.StafferCreateRequest;
import me.weekbelt.apiserver.staffer.dto.StafferResponse;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.mapper.StafferMapper;
import me.weekbelt.persistence.staffer.Staffer;
import me.weekbelt.persistence.staffer.service.StafferDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StafferService {

    private final DepartmentDataService departmentDataService;

    private final StafferDataService stafferDataService;

    @Transactional
    public StafferResponse save(StafferCreateRequest createRequest) {
        Department department = departmentDataService.getById(createRequest.getDepartmentId());
        Staffer staffer = Staffer.create(department, createRequest);
        return StafferMapper.toStafferResponse(stafferDataService.save(staffer));
    }
}
