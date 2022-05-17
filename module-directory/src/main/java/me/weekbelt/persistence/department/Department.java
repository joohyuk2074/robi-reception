package me.weekbelt.persistence.department;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.persistence.Phone;

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

    @Builder
    public Department(String id, String name, Phone phone, String branchId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.branchId = branchId;
    }
}
