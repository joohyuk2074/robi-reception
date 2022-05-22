package me.weekbelt.persistence.department.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.department.DepartmentSynonym;
import me.weekbelt.persistence.department.repository.DepartmentSynonymRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentSynonymDataService {

    private final DepartmentSynonymRepository departmentSynonymRepository;

    public void saveAll(List<DepartmentSynonym> departmentSynonyms) {
        departmentSynonymRepository.saveAll(departmentSynonyms);
    }
}
