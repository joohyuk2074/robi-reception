package me.weekbelt.persistence.department.repository;

import me.weekbelt.persistence.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
