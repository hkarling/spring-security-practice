package io.hkarling.common.jwt;

import io.hkarling.member.entity.Member;
import io.hkarling.member.repository.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                //.map(this::createUserDetails)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> cannot find in database"));
    }

//    private UserDetails createUserDetails(Member member) {
//        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + member.getRole().name());
//        return new CustomUserDetails(member.getId(), member.getUsername(), member.getPassword(), Collections.singleton(authority));
//    }

}
