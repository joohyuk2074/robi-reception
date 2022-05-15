package me.weekbelt.auth.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.auth.entity.Member;
import me.weekbelt.auth.repository.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberDataService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getUsers() {
        return memberRepository.findAll();
    }

    public Member getUserByUsername(String username) {
        return memberRepository.getByUsername(username)
            .orElseThrow(() -> {
                log.debug("User not found in database");
                throw new UsernameNotFoundException("No user found with username: " + username);
            });
    }
}
