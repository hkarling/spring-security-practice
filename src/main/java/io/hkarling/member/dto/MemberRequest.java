package io.hkarling.member.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class MemberRequest {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String position;
    private String avatar;

}
