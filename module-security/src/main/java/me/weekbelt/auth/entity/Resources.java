package me.weekbelt.auth.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String resourceName;

    @Column
    private String httpMethod;

    @Column
    private int orderNum;

    @Column
    private String resourceType;

    @Builder
    public Resources(String resourceName, String httpMethod, int orderNum, String resourceType) {
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
    }

    @OneToMany(mappedBy = "resources", fetch = FetchType.LAZY)
    private Set<RoleResources> roleResources = new HashSet<>();
}
