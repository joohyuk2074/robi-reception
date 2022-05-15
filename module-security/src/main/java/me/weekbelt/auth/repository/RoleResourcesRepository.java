package me.weekbelt.auth.repository;

import me.weekbelt.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourcesRepository extends JpaRepository<Role, Long> {
}
