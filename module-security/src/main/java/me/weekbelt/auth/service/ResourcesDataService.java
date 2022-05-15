package me.weekbelt.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.repository.ResourcesRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResourcesDataService {

    private final ResourcesRepository resourcesRepository;
}
