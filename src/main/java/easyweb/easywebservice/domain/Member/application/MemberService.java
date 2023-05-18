package easyweb.easywebservice.domain.Member.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import easyweb.easywebservice.domain.Member.dto.MemberDTO;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.MemberUpdateDto;
import easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.global.error.exception.NotFoundByIdException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    /**
     * 로그인한 멤버 정보 조회 메서드
     * @param currentMemberId 로그인한 멤버 아이디
     * @return 아이디로 찾은 멤버
     */
    @Transactional
    public MemberInfoQ getLoginMemberInfo(Long currentMemberId) {
        return memberRepository.findMemberInfoById(currentMemberId).orElseThrow(NotFoundByIdException::new);
    }

    /**
     * 멤버 정보 업데이트 메서드
     * @param updates 업데이트할 데이터 map
     * @param memberId 업데이트할 멤버 아이디
     * @return 업데이트된 정보
     */
    @Transactional
    public MemberUpdateDto updateMemberInfo(Map<String, Object> updates, Long memberId) {
        MemberUpdateDto memberInfoQ = objectMapper.convertValue(updates, MemberUpdateDto.class);
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundByIdException::new);
        if (memberInfoQ.getNickname() != null) {
            member.updateNickName(memberInfoQ.getNickname());
        }
        return memberInfoQ;
    }

}
