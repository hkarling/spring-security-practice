package io.hkarling.member.controller;

import io.hkarling.common.jwt.TokenRequest;
import io.hkarling.common.jwt.TokenResponse;
import io.hkarling.member.dto.LoginRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.dto.SignupRequest;
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

@Api(tags = "Member Information")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "Sign up")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @ApiOperation(value = "AccessToken reissue")
    @PutMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }

    @ApiOperation(value = "Check duplicated username")
    @GetMapping("/duplicate")
    public ResponseEntity<Boolean> duplicateCheckByUserId(@RequestParam String username) {
        return ResponseEntity.ok(authService.duplicate(username));
    }

}
