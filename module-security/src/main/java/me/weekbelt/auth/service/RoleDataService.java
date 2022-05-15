package me.weekbelt.auth.service;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.entity.Role;
import me.weekbelt.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleDataService {

    private final RoleRepository roleRepository;

    public Role getByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find roleName : " + roleName));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
