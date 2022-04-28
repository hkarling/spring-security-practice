package io.hkarling.member.controller;

import io.hkarling.common.jwt.TokenRequest;
import io.hkarling.common.jwt.TokenResponse;
import io.hkarling.member.dto.LoginRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.dto.MemberRequest;
import io.hkarling.member.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "인증 관리")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "사용자 가입")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @ApiOperation(value = "Access Token 재발행")
    @PutMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }

    @ApiOperation(value = "username 중복확인")
    @GetMapping("/duplicate")
    public ResponseEntity<Boolean> duplicateCheckByUserId(@RequestParam String username) {
        return ResponseEntity.ok(authService.duplicate(username));
    }

}
