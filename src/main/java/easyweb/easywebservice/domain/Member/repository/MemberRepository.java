package easyweb.easywebservice.domain.Member.repository;

import easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ;
import easyweb.easywebservice.domain.Member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndDeleted(String email, Boolean deleted);

    boolean existsByEmailAndDeleted(String email, Boolean deleted);
    @Query("SELECT NEW easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ(m.id, m.username, m.nickname, m.email) FROM Member m WHERE m.id=:id")
    Optional<MemberInfoQ> findMemberInfoById(@Param("id") Long id);
}
