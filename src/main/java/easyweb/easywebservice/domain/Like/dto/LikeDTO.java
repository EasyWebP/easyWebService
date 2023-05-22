package easyweb.easywebservice.domain.Like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "좋아요 생성 요청")
    public static class LikeCreateDto {
        private Long productId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "좋아요 해제 요청")
    public static class LikeDeleteDto {
        private Long productId;
    }
}
