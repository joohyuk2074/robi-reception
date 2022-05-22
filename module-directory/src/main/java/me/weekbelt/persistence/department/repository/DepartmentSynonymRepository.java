package me.weekbelt.persistence.department.repository;

import me.weekbelt.persistence.department.DepartmentSynonym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentSynonymRepository extends JpaRepository<DepartmentSynonym, String> {
}
