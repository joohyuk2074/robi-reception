package me.weekbelt.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.repository.RoleResourcesRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleResourceDataService {

    private final RoleResourcesRepository roleResourcesRepository;
}
