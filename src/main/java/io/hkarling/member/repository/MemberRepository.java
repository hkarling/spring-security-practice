package io.hkarling.member.repository;

import io.hkarling.member.entity.Member;
import io.hkarling.member.enums.Role;
import io.hkarling.member.repository.query.MemberQueryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findByUsername(String username);

    List<Member> findByRole(Role role);

}
