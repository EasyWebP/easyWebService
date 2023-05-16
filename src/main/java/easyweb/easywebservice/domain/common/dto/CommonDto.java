package easyweb.easywebservice.domain.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommonDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class StringApiResult {
        @Schema(description = "API 호출 결과")
        private String result;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BooleanApiResult {
        @Schema(description = "API 호출 결과")
        private Boolean result;
    }

}

