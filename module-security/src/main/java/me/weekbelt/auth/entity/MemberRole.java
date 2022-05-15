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
public class MemberRole extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getMemberRoles().remove(this);
        }
        this.member = member;
        member.getMemberRoles().add(this);
    }

    public void setRole(Role role) {
        if (this.role != null) {
            this.role.getMemberRoles().remove(this);
        }

        this.role = role;
        role.getMemberRoles().add(this);
    }
}
