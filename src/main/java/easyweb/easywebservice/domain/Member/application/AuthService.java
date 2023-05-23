package easyweb.easywebservice.domain.Member.application;

import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.domain.Cart.repository.CartRepository;
import easyweb.easywebservice.domain.Member.dto.MemberDTO;
import easyweb.easywebservice.domain.Member.dto.MemberDTO.*;
import easyweb.easywebservice.domain.Member.exception.MemberExistsWithEmailException;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Token.dto.TokenDTO;
import easyweb.easywebservice.domain.Token.dto.TokenDTO.TokenInfoDTO;
import easyweb.easywebservice.domain.Token.exception.ExpireRefreshTokenException;
import easyweb.easywebservice.domain.Token.exception.InvalidRefreshTokenException;
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
    private final CartRepository cartRepository;
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
        Cart build = Cart.builder().member(entity).count(0).build();
        cartRepository.save(build);
        return new StringApiResult("CREATED");

    }

    /**
     * 회원가입시 이메일 중복 여부 확인 메서드
     * @param email 중복확인할 이메일
     * @return 중복 확인 결과
     */
    @Transactional
    public BooleanApiResult checkEmailExistence(String email) {
        return new BooleanApiResult(memberRepository.existsByEmailAndDeleted(email, false));
    }

    @Transactional
    public BooleanApiResult checkNicknameExistence(String nickname) {
        return new BooleanApiResult(memberRepository.existsByNicknameAndDeleted(nickname, false));
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

        TokenInfoDTO tokenInfoDTO = jwtTokenProvider.generateTokenDto(authenticate);
        log.info("로그인 API 중 토큰 생성 로직 실행");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authenticate.getName(), tokenInfoDTO.getRefreshToken());
        redisTemplate.expire(authenticate.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return LoginResult.builder()
                .memberInfo(memberRepository.findMemberInfoById(Long.parseLong(authenticate.getName())).get())
                .tokenInfo(tokenInfoDTO.toTokenIssueDTO())
                .build();
    }

    @Transactional
    public LoginResult socialLogin(SocialLoginDto socialLoginDto) {
        Member entity = socialLoginDto.toEntity(passwordEncoder);
        memberRepository.save(entity);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = socialLoginDto.toAuthentication();

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        TokenInfoDTO tokenInfoDTO = jwtTokenProvider.generateTokenDto(authenticate);
        log.info("로그인 API 중 토큰 생성 로직 실행");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authenticate.getName(), tokenInfoDTO.getRefreshToken());
        redisTemplate.expire(authenticate.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return LoginResult.builder()
                .memberInfo(memberRepository.findMemberInfoById(Long.parseLong(authenticate.getName())).get())
                .tokenInfo(tokenInfoDTO.toTokenIssueDTO())
                .build();
    }

    /**
     * 토큰 재발급 메서드
     * @param reissueRequest 재발급 요청
     * @return 토큰 재발급 결과
     */
    @Transactional
    public TokenDTO.TokenIssueDTO reissue(TokenDTO.ReissueRequest reissueRequest) {

        String accessToken = reissueRequest.getToken();

        log.info("access : " + accessToken);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // Access Token에서 멤버 아이디 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String refreshByAccess = valueOperations.get(authentication.getName());
        if (refreshByAccess == null) {
            log.info("토큰 재발급 API 중 리프레쉬 만료 확인");
            throw new ExpireRefreshTokenException();
        }
        // refresh token 검증
        if (!jwtTokenProvider.validateToken(refreshByAccess)) {
            log.info("토큰 재발급 API 중 유효하지 않은 리프레쉬 확인");
            throw new InvalidRefreshTokenException();
        }


        // 새로운 토큰 생성
        TokenInfoDTO tokenInfoDTO = jwtTokenProvider.generateTokenDto(authentication);
        // 저장소 정보 업데이트
        log.info("토큰 재발급 성공후 레디스에 값 저장");
        valueOperations.set(authentication.getName(), tokenInfoDTO.getRefreshToken());
        redisTemplate.expire(authentication.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);


        // 토큰 발급
        return tokenInfoDTO.toTokenIssueDTO();
    }
}

