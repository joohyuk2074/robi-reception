package me.weekbelt.auth.mapper;

import java.util.List;
import java.util.stream.Collectors;
import me.weekbelt.apiserver.auth.dto.MemberResponse;
import me.weekbelt.apiserver.auth.dto.MemberSaveRequest;
import me.weekbelt.auth.entity.Member;
import me.weekbelt.auth.entity.MemberRole;
import me.weekbelt.auth.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberMapper {

    public static Member toMember(MemberSaveRequest memberSaveRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .username(memberSaveRequest.getUsername())
            .password(passwordEncoder.encode(memberSaveRequest.getPassword()))
            .name(memberSaveRequest.getName())
            .build();
    }

    public static MemberResponse toMemberResponse(Member member) {
        List<String> userRoles = member.getMemberRoles().stream()
            .map(userRole -> userRole.getRole().getRoleName())
            .collect(Collectors.toList());

        return MemberResponse.builder()
            .id(member.getId())
            .username(member.getUsername())
            .name(member.getName())
            .userRoles(userRoles)
            .build();
    }

    public static MemberRole toMemberRole(Member member, Role role) {
        MemberRole memberRole = new MemberRole();
        memberRole.setMember(member);
        memberRole.setRole(role);
        return memberRole;
    }
}
