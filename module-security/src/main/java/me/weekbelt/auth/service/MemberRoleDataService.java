package me.weekbelt.auth.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.weekbelt.auth.entity.MemberRole;
import me.weekbelt.auth.repository.MemberRoleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberRoleDataService {

    private final MemberRoleRepository memberRoleRepository;

    public void saveAll(Set<MemberRole> memberRoles) {
        memberRoleRepository.saveAll(memberRoles);
    }
}

