package io.hkarling.member.repository;

import io.hkarling.member.entity.Member;
import io.hkarling.member.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void save() {

        for(int i = 0; i < 200; i++) {
            String format = String.format("%03d", i+1);
            memberRepository.save(Member.builder()
                    .username("member" + format)
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.MEMBER)
                    .name("사용자"+ format)
                    .email("member" + format + "@hkarling.io")
                    .phone("000-0000-0000")
                    .department("Developer Grp #" + String.format("%02d", (i/10 + 1)))
                    .position("Member")
                    .avatar("avatar")
                    .build());
        }

    }

}