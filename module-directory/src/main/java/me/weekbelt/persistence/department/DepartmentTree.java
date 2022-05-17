package me.weekbelt.persistence.department;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DepartmentTree {

    @Id
    private String id;

    private String ancestor;

    private String descendant;

    private Integer depth;

    private String branchId;
}
