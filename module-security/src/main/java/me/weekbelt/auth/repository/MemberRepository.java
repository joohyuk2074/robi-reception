package me.weekbelt.auth.repository;

import java.util.Optional;
import me.weekbelt.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> getByUsername(String username);
}
