package me.weekbelt.persistence.department.repository;

import java.util.List;
import me.weekbelt.persistence.department.DepartmentTree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentTreeRepository extends JpaRepository<DepartmentTree, String> {

    List<DepartmentTree> findByDescendant(String descendant);

    List<DepartmentTree> findByAncestorAndDepth(String ancestor, int depth);

    List<DepartmentTree> findByAncestor(String ancestor);
}
