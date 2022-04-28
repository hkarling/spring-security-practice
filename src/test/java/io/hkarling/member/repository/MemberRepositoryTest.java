package io.hkarling.member.repository;

import io.hkarling.member.entity.Member;
import io.hkarling.member.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @Commit
    void save() {
        for(int i = 0; i < 8; i++) {
            String format = String.format("%02d", i+1);
            memberRepository.save(Member.builder()
                    .username("admin-user" + format)
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .name("관리자"+ format)
                    .email("admin" + format + "@hkarling.io")
                    .phone("000-0000-0000")
                    .department("Developer Grp #" + String.format("%02d", (i/10 + 1)))
                    .position("시스템관리자")
                    .avatar("avatar")
                    .build());
        }

        for(int i = 0; i < 200; i++) {
            String format = String.format("%03d", i+1);
            memberRepository.save(Member.builder()
                    .username("member" + format)
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.MEMBER)
                    .name("멤버"+ format)
                    .email("member" + format + "@hkarling.io")
                    .phone("000-0000-0000")
                    .department("Developer Grp #" + String.format("%02d", (i/10 + 1)))
                    .position("시스템사용자")
                    .avatar("avatar")
                    .build());
        }

    }

    @Test
    void findByUsername() {
        Member member001 = memberRepository.findByUsername("member001").orElseThrow(RuntimeException::new);
        System.out.println("member001 = " + member001);
    }
}