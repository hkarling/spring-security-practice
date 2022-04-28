package io.hkarling.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.hkarling.member.dto.LoginRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.dto.MemberRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Test
    void signup() {
        MemberRequest request = MemberRequest.builder()
                .username("admin-user")
                .password("1234")
                .name("관리자")
                .email("admin@hkarling.io")
                .phone("000-0000-0000")
                .department("Administration")
                .position("ADMIN")
                .avatar("avatar")
                .build();
        MemberResponse signup = authService.signup(request);

        assertThat(signup.getName()).isEqualTo(request.getName());
        assertThat(signup.getId()).isNotNull();

        Assertions.assertThrows(RuntimeException.class, () -> authService.signup(request));
    }

    @Test
    void login() {
        LoginRequest loginRequest = LoginRequest.builder().username("admin-user").password("1234sdf").build();
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
    }

}