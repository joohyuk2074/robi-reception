package me.weekbelt.persistence.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(exclude = {"id", "department"})
@Getter
@NoArgsConstructor
@Entity
public class DepartmentSynonym {

    @Id
    private String id;

    @Column(nullable = false)
    private String synonym;

    @Column
    private String branchId;

    @JoinColumn(name = "department_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Builder
    public DepartmentSynonym(String id, String synonym, String branchId) {
        this.id = id;
        this.synonym = synonym;
        this.branchId = branchId;
    }

    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.getDepartmentSynonyms().remove(this);
        }

        this.department = department;
        department.getDepartmentSynonyms().add(this);
    }
}
