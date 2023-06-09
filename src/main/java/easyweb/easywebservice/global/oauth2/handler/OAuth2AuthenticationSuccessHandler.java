package easyweb.easywebservice.global.oauth2.handler;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Token.dto.TokenDTO.TokenInfoDTO;
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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
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

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication success");



        log.info("authentication : " + authentication.getName());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String targetUrl;
        if (Objects.equals(authentication.getName(), "null")) {
            targetUrl = determineTargetUrlForFirstLogin(request, response, authentication, attributes);
        } else {
            targetUrl = determineTargetUrlForLoginAgain(request, response, authentication);
        }


        if (response.isCommitted()) {
            return;
        }

        log.info("target url : " + targetUrl);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrlForLoginAgain(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        //토큰 생성
        TokenInfoDTO tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // redis에 쿠키 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 기존에 회원가입한 멤버 존재

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenDto.getAccessToken())
                .queryParam("created", false)
                .build();

        valueOperations.set(authentication.getName(), tokenDto.getRefreshToken());
        redisTemplate.expire(authentication.getName(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return uriComponents.toUriString();


    }

    protected String determineTargetUrlForFirstLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication,  Map<String, Object> attributes) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        UriComponents uriComponents;
        if (attributes.containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
            uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("created", true)
                    .queryParam("email", kakaoAccount.get("email"))
                    .queryParam("username", URLEncoder.encode(profile.get("nickname"), StandardCharsets.UTF_8))
                    .queryParam("loginType","KAKAO")
                    .build();
        } else {

            uriComponents = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("created", true)
                    .queryParam("email", attributes.get("email"))
                    .queryParam("username", URLEncoder.encode((String) attributes.get("name"), StandardCharsets.UTF_8))
                    .queryParam("loginType", "GOOGLE")
                    .build();
        }

        return uriComponents.toUriString();
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }


}
