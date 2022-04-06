package io.hkarling.member.service;

import io.hkarling.common.errors.CommonException;
import io.hkarling.common.errors.ErrorCode;
import io.hkarling.common.jwt.TokenProvider;
import io.hkarling.common.jwt.TokenRequest;
import io.hkarling.common.jwt.TokenResponse;
import io.hkarling.member.dto.LoginRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.dto.SignupRequest;
import io.hkarling.member.entity.Member;
import io.hkarling.member.entity.RefreshToken;
import io.hkarling.member.enums.Role;
import io.hkarling.member.repository.MemberRepository;
import io.hkarling.member.repository.RefreshTokenRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponse signup(SignupRequest request) {
        memberRepository.findByUsername(request.getUsername()).ifPresent(member -> {
            throw new CommonException(ErrorCode.USER_EXISTS);
        });

        Member member = modelMapper.map(request, Member.class);
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setRole(Role.MEMBER);

        return modelMapper.map(memberRepository.save(member), MemberResponse.class);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        TokenResponse token = tokenProvider.generateToken(auth);
        RefreshToken refreshToken = refreshTokenRepository.findById(auth.getName()).orElse(new RefreshToken(auth.getName(), "", null));
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(token.getRefreshTokenExpiry()), ZoneId.systemDefault());
        refreshToken.updateValue(token.getRefreshToken(), time);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequest) {
        if (!tokenProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new CommonException(ErrorCode.TOKEN_ILLEGAL);
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new CommonException(ErrorCode.USER_LOGOUT));

        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new CommonException(ErrorCode.TOKEN_UNMATCHED);
        }

        TokenResponse tokenResponse = tokenProvider.generateToken(authentication);
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(tokenResponse.getRefreshTokenExpiry()), ZoneId.systemDefault());
        refreshToken.updateValue(tokenResponse.getRefreshToken(), time);

        return tokenResponse;
    }

    @Transactional(readOnly = true)
    public Boolean duplicate(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

}
