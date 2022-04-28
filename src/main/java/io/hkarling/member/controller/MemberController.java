package io.hkarling.member.controller;

import io.hkarling.member.dto.MemberRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.enums.Role;
import io.hkarling.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.annotation.Nullable;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "사용자 관리")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "사용자 리스트 조회")
    @GetMapping("/member")
    public ResponseEntity<List<MemberResponse>> findMemberList(@RequestParam(required = false) @Nullable Role role) {
        return role == null ? ResponseEntity.ok(memberService.findMemberList()) : ResponseEntity.ok(memberService.findMemberList(role));
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "로그인 사용자 조회")
    @GetMapping("/member-me")
    public ResponseEntity<MemberResponse> getLoginMember(@ApiIgnore Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(memberService.findByUsername(username));
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "사용자 조회")
    @GetMapping("/member/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "사용자 수정(ADMIN)")
    @PutMapping("/member/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.username")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "사용자 삭제(ADMIN)")
    @DeleteMapping("/member/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        return ResponseEntity.status(memberService.deleteMember(id)).build();
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "패스워드 초기화")
    @PatchMapping("/member/{id}/reset")
    public ResponseEntity<String> resetPassword(@PathVariable String id, @RequestBody String password) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(authorizations = {@Authorization("Bearer")}, value = "패스워드 변경")
    @PatchMapping("/member/{id}/change")
    public ResponseEntity<String> changePassword(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }


}
