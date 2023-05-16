package easyweb.easywebservice.domain.Token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

import static easyweb.easywebservice.global.common.JwtConstants.REFRESH_TOKEN_EXPIRE_TIME;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String userId;


    private String refreshToken;

    @Indexed
    private String accessToken;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expired;

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
        this.expired = REFRESH_TOKEN_EXPIRE_TIME;
    }
}

