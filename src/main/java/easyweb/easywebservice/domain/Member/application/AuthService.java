package easyweb.easywebservice.domain.Member.application;

import easyweb.easywebservice.domain.Member.dto.MemberDTO;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.EmailExistenceDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginDto;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.LoginResult;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.SignUpDto;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.common.dto.CommonDto;
import easyweb.easywebservice.domain.common.dto.CommonDto.BooleanApiResult;
import easyweb.easywebservice.domain.common.dto.CommonDto.StringApiResult;
import easyweb.easywebservice.global.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return null;

    }

    @Transactional
    public BooleanApiResult checkEmailExistence(EmailExistenceDto emailExistenceDto) {
        return null;
    }

    @Transactional
    public LoginResult login(LoginDto loginDto) {
        return null;
    }
}

