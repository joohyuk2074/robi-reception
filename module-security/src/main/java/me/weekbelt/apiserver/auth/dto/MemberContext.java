package me.weekbelt.apiserver.auth.dto;

import java.util.List;
import lombok.Getter;
import me.weekbelt.auth.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberContext extends User {

    private final Member member;

    public MemberContext(Member member, List<GrantedAuthority> roles) {
        super(member.getUsername(), member.getPassword(), roles);
        this.member = member;
    }
}
