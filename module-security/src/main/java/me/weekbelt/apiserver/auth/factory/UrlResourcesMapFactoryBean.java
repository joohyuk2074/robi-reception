package me.weekbelt.apiserver.auth.factory;

import java.util.LinkedHashMap;
import java.util.List;
import me.weekbelt.apiserver.auth.service.SecurityResourcesService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private SecurityResourcesService securityResourcesService;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap;

    public void setSecurityResourcesService(SecurityResourcesService securityResourcesService) {
        this.securityResourcesService = securityResourcesService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() {
        if (resourcesMap == null) {
            init();
        }

        return resourcesMap;
    }

    private void init() {
        resourcesMap = securityResourcesService.getResourceList();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
