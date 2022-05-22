package me.weekbelt.persistence.department.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.department.DepartmentSynonym;
import me.weekbelt.persistence.department.repository.DepartmentSynonymRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentSynonymDataService {

    private final DepartmentSynonymRepository departmentSynonymRepository;

    public DepartmentSynonym getById(String synonymId) {
        return departmentSynonymRepository.findById(synonymId)
            .orElseThrow(()->new EntityNotFoundException("찾는 DepartmentSynonym이 존재하지 않습니다. synonymId: " + synonymId));
    }
}
