package me.weekbelt.persistence.department;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.apiserver.department.dto.DepartmentUpdateRequest;
import me.weekbelt.persistence.Phone;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@Entity
public class Department {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Embedded
    private Phone phone;

    private String branchId;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DepartmentSynonym> departmentSynonyms = new HashSet<>();

    @Builder
    public Department(String id, String name, Phone phone, String branchId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.branchId = branchId;
    }

    public void update(DepartmentUpdateRequest updateRequest) {
        if (StringUtils.hasText(updateRequest.getName())) {
            this.name = updateRequest.getName();
        }

        if (StringUtils.hasText(updateRequest.getNumber())) {
            this.phone.updateNumber(updateRequest.getNumber());
        }

        if (!ObjectUtils.isEmpty(updateRequest.getPhoneType())) {
            this.phone.updatePhoneType(updateRequest.getPhoneType());
        }
    }

    public void addSynonyms(List<String> synonyms) {
        Set<DepartmentSynonym> departmentSynonyms = Set.copyOf(synonyms).stream()
            .filter(StringUtils::hasText)
            .map(this::createDepartmentSynonym)
            .collect(toSet());

        this.departmentSynonyms.clear();
        this.departmentSynonyms.addAll(departmentSynonyms);
    }

    private DepartmentSynonym createDepartmentSynonym(String synonym) {
        DepartmentSynonym departmentSynonym = DepartmentSynonym.builder()
            .id(UUID.randomUUID().toString())
            .synonym(synonym)
            .build();
        departmentSynonym.setDepartment(this);
        return departmentSynonym;
    }

    public void removeSynonym(DepartmentSynonym departmentSynonym) {
        this.getDepartmentSynonyms().remove(departmentSynonym);
    }
}
