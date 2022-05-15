package me.weekbelt.auth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RoleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "resources_id")
    private Resources resources;


    public void setRole(Role role) {
        if (this.role != null) {
            this.role.getRoleResources().remove(this);
        }

        this.role = role;
        role.getRoleResources().add(this);
    }

    public void setResources(Resources resources) {
        if (this.resources != null) {
            this.resources.getRoleResources().remove(this);
        }

        this.resources = resources;
        role.getRoleResources().add(this);
    }
}
