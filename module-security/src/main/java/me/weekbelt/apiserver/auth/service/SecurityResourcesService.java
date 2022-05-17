package me.weekbelt.apiserver.auth.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.entity.Resources;
import me.weekbelt.auth.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityResourcesService {

    private final ResourcesRepository resourcesRepository;

    @Transactional(readOnly = true)
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();

        List<Resources> resourcesList = resourcesRepository.findAllResources();
        resourcesList.forEach(resources -> {
            result.put(getRequestMatcher(resources), getConfigAttributeList(resources));
        });

        return result;
    }

    private AntPathRequestMatcher getRequestMatcher(Resources resources) {
        return new AntPathRequestMatcher(resources.getResourceName());
    }

    private List<ConfigAttribute> getConfigAttributeList(Resources resources) {
        List<ConfigAttribute> configAttributeList = new ArrayList<>();

        resources.getRoleResources().forEach(roleResources -> {
            configAttributeList.add(new SecurityConfig(roleResources.getRole().getRoleName()));
        });

        return configAttributeList;
    }
}
