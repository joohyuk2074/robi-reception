package me.weekbelt.apiserver.auth.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.apiserver.auth.dto.MemberContext;
import me.weekbelt.apiserver.auth.dto.MemberResponse;
import me.weekbelt.apiserver.auth.dto.MemberSaveRequest;
import me.weekbelt.apiserver.auth.dto.RoleResponse;
import me.weekbelt.auth.entity.Member;
import me.weekbelt.auth.entity.MemberRole;
import me.weekbelt.auth.entity.Role;
import me.weekbelt.auth.mapper.MemberMapper;
import me.weekbelt.auth.mapper.RoleMapper;
import me.weekbelt.auth.service.MemberDataService;
import me.weekbelt.auth.service.MemberRoleDataService;
import me.weekbelt.auth.service.RoleDataService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberDataService memberDataService;

    private final MemberRoleDataService memberRoleDataService;

    private final RoleDataService roleDataService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDataService.getUserByUsername(username);

        List<GrantedAuthority> roles = member.getMemberRoles().stream()
            .map(memberRole -> new SimpleGrantedAuthority(memberRole.getRole().getRoleName()))
            .collect(toList());

        return new MemberContext(member, roles);
    }

    @Transactional
    public RoleResponse save(String roleName) {
        Role role = Role.builder()
            .roleName(roleName)
            .build();
        Role savedRole = roleDataService.save(role);
        return RoleMapper.toRoleResponse(savedRole);
    }

    @Transactional
    public MemberResponse save(MemberSaveRequest memberSaveRequest) {
        Member member = createMember(memberSaveRequest);
        Member savedMember = memberDataService.save(member);

        Set<MemberRole> memberRoles = createMemberRoles(memberSaveRequest, savedMember);
        memberRoleDataService.saveAll(memberRoles);

        return MemberMapper.toMemberResponse(savedMember);
    }

    private Member createMember(MemberSaveRequest memberSaveRequest) {
        return MemberMapper.toMember(memberSaveRequest, passwordEncoder);
    }

    private Set<MemberRole> createMemberRoles(MemberSaveRequest memberSaveRequest, Member member) {
        List<String> roles = memberSaveRequest.getRoles();
        return roles.stream()
            .map(roleName -> {
                Role role = roleDataService.getByRoleName(roleName);
                return MemberMapper.toMemberRole(member, role);
            }).collect(toSet());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberDataService.getUsers()
            .stream().map(MemberMapper::toMemberResponse)
            .collect(toList());
    }
}
