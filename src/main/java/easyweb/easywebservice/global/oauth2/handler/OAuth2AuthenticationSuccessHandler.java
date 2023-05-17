package easyweb.easywebservice.global.oauth2.handler;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Token.dto.TokenDTO;
import easyweb.easywebservice.global.jwt.JwtTokenProvider;
import easyweb.easywebservice.global.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import easyweb.easywebservice.global.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static easyweb.easywebservice.global.common.JwtConstants.REFRESH_TOKEN_EXPIRE_TIME;
import static easyweb.easywebservice.global.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication success");



        log.info("authentication : "+authentication.getName());
        Optional<Member> byId = memberRepository.findById(Long.parseLong(authentication.getName()));

        if (response.isCommitted()) {
            return;
        }
        String targetUrl = determineTargetUrl(request, response, authentication, byId);
        log.info("target url : "+targetUrl);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Optional<Member> byId) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        //토큰 생성
        TokenDTO.TokenInfoDTO tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // redis에 쿠키 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authentication.getName(), tokenDto.getRefreshToken());
        redisTemplate.expire(authentication.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        if (byId.isPresent()) {
            Member member = byId.get();
            UriComponents uriComponents;
            if (member.getNickname() == null) {
                uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("token", tokenDto.getAccessToken())
                        .queryParam("email", member.getEmail())
                        .queryParam("created", true)
                        .build();
            } else {
                uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("token", tokenDto.getAccessToken())
                        .queryParam("email", member.getEmail())
                        .queryParam("created", false)
                        .build();
            }
            return uriComponents.toUriString();


        } else {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("token", tokenDto.getAccessToken())
                    .queryParam("email", "NULL")
                    .build();
            return uriComponents.toUriString();
        }


    }
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }


}
