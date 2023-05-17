package easyweb.easywebservice.domain.Member.application;

import easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.global.error.exception.NotFoundByIdException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 로그인한 멤버 정보 조회 메서드
     * @param currentMemberId 로그인한 멤버 아이디
     * @return 아이디로 찾은 멤버
     */
    @Transactional
    public MemberInfoQ getLoginMemberInfo(Long currentMemberId) {
        return memberRepository.findMemberInfoById(currentMemberId).orElseThrow(NotFoundByIdException::new);
    }
}
