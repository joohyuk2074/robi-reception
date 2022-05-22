package me.weekbelt.persistence.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class DepartmentSynonym {

    @Id
    private String id;

    @Column(nullable = false)
    private String synonym;

    @JoinColumn(name = "department_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Builder
    public DepartmentSynonym(String id, String synonym) {
        this.id = id;
        this.synonym = synonym;
    }

    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.getDepartmentSynonyms().remove(this);
        }

        this.department = department;
        department.getDepartmentSynonyms().add(this);
    }
}
