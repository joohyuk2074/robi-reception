package me.weekbelt.auth.repository;

import java.util.List;
import me.weekbelt.auth.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    Resources findByResourceNameAndHttpMethod(String resourceName, String httpMethod);

    @Query("select r from Resources r join fetch r.roleResources where r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();

    @Query("select r from Resources r join fetch r.roleResources where r.resourceType = 'method' order by r.orderNum desc")
    List<Resources> findAllMethodResources();

    @Query("select r from Resources r join fetch r.roleResources where r.resourceType = 'pointcut' order by r.orderNum desc")
    List<Resources> findAllPointcutResources();

}
