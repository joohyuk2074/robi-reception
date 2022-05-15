package me.weekbelt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecurityPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityPlaygroundApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(MemberService memberService) {
//        return args -> {
//            memberService.save("ROLE_USER");
//            memberService.save("ROLE_ADMIN");
//
//            MemberSaveRequest adminMemberSaveRequest = MemberSaveRequest.builder()
//                .username("weekbelt")
//                .name("김주혁")
//                .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
//                .build();
//            memberService.save(adminMemberSaveRequest);
//            MemberSaveRequest userMemberSaveRequest = MemberSaveRequest.builder()
//                .username("vfrvfr4207")
//                .name("김현수")
//                .roles(List.of("ROLE_USER"))
//                .build();
//            memberService.save(userMemberSaveRequest);
//        };
//    }
}
