package me.weekbelt.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.repository.RoleHierarchyRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleHierarchyDataService {

    private final RoleHierarchyRepository roleHierarchyRepository;
}
