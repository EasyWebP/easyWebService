package easyweb.easywebservice.domain.Category.dto;

import easyweb.easywebservice.domain.Category.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "카테고리 생성 요청 DTO")
    public static class CategoryCreateDto {

        @Schema(description = "카테고리 생성 요청")
        private String name;

        public Category toEntity() {
            return Category.builder()
                    .name(name)
                    .build();
        }
    }
}
