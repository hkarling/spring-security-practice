package io.hkarling.member.service;

import io.hkarling.common.errors.CommonException;
import io.hkarling.common.errors.ErrorCode;
import io.hkarling.member.dto.MemberRequest;
import io.hkarling.member.dto.MemberResponse;
import io.hkarling.member.entity.Member;
import io.hkarling.member.enums.Role;
import io.hkarling.member.repository.MemberRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<MemberResponse> findMemberList() {
        return memberRepository.findAll().stream()
                .sorted(Comparator.comparing(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findMemberList(Role role) {
        return memberRepository.findByRole(role).stream()
                .sorted(Comparator.comparing(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        return modelMapper.map(member, MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public MemberResponse findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        return modelMapper.map(member, MemberResponse.class);
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        Member changed = member.updateBasicInfo(request);
        return modelMapper.map(changed, MemberResponse.class);
    }

    @Transactional
    public HttpStatus deleteMember(Long id) {
        memberRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

}
