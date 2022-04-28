package io.hkarling.member.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import io.hkarling.common.entity.BaseEntity;
import io.hkarling.member.dto.MemberRequest;
import io.hkarling.member.enums.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Entity
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String position;
    private String avatar;
    @Enumerated(STRING)
    private Role role;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Member updateBasicInfo(MemberRequest request) {
        this.email = request.getEmail();
        this.phone = request.getPhone();
        this.department = request.getDepartment();
        this.position = request.getPosition();
        this.avatar = request.getAvatar();
        return this;
    }

}
