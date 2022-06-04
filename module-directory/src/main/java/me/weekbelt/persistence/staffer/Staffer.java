package me.weekbelt.persistence.staffer;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.apiserver.staffer.dto.StafferCreateRequest;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.mapper.StafferMapper;

@Getter
@NoArgsConstructor
@Entity
public class Staffer {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    @Embedded
    private Phone phone;

    @Column(nullable = false)
    private String branchId;

    @Builder
    public Staffer(String id, String name, Phone phone, String branchId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.branchId = branchId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public static Staffer create(Department department, StafferCreateRequest createRequest) {
        Staffer staffer = StafferMapper.toStaffer(createRequest);
        staffer.setDepartment(department);
        return staffer;
    }

    private void setDepartment(Department department) {
        this.department = department;
    }
}
