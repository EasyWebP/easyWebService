package easyweb.easywebservice.domain.Member.repository;

import easyweb.easywebservice.domain.Member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndDeleted(String email, Boolean deleted);

    boolean existsByEmailAndDeleted(String email, Boolean deleted);

    boolean existsByNickNameAndDeleted(String nickname, Boolean deleted);
}
