package easyweb.easywebservice.domain.Member.application;

import easyweb.easywebservice.domain.Member.dto.MemberDTO;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.EmailExistenceDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginResult;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.SignUpDto;
import easyweb.easywebservice.domain.Member.exception.MemberExistsWithEmailException;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Token.dto.TokenDTO;
import easyweb.easywebservice.domain.common.dto.CommonDto;
import easyweb.easywebservice.domain.common.dto.CommonDto.BooleanApiResult;
import easyweb.easywebservice.domain.common.dto.CommonDto.StringApiResult;
import easyweb.easywebservice.global.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static easyweb.easywebservice.global.common.JwtConstants.REFRESH_TOKEN_EXPIRE_TIME;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원가입 메서드
     * @param signUpDto 회원가입 요청 데이터
     * @return 회원가입 결과
     */
    @Transactional
    public StringApiResult signUp(SignUpDto signUpDto) {
        if (memberRepository.existsByEmailAndDeleted(signUpDto.getEmail(), false)) {
            throw new MemberExistsWithEmailException();
        }

        signUpDto.encode(passwordEncoder);
        Member entity = signUpDto.toEntity();
        memberRepository.save(entity);
        return new StringApiResult("CREATED");

    }

    /**
     * 회원가입시 이메일 중복 여부 확인 메서드
     * @param emailExistenceDto 중복확인할 이메일
     * @return 중복 확인 결과
     */
    @Transactional
    public BooleanApiResult checkEmailExistence(EmailExistenceDto emailExistenceDto) {
        return new BooleanApiResult(memberRepository.existsByEmailAndDeleted(emailExistenceDto.getEmail(), false));
    }

    /**
     * 로그인 메서드
     * @param loginDto 로그인할 아이디 비밀번호
     * @return 로그인결과
     */
    @Transactional
    public LoginResult login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = loginDto.toAuthentication();

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        TokenDTO.TokenInfoDTO tokenInfoDTO = jwtTokenProvider.generateTokenDto(authenticate);
        log.info("로그인 API 중 토큰 생성 로직 실행");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authenticate.getName(), tokenInfoDTO.getRefreshToken());
        redisTemplate.expire(authenticate.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return LoginResult.builder()
                .memberInfo(memberRepository.findMemberInfoById(Long.parseLong(authenticate.getName())))
                .tokenInfo(tokenInfoDTO.toTokenIssueDTO())
                .build();
    }
}

