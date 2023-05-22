package easyweb.easywebservice.domain.Category.api;

import easyweb.easywebservice.domain.Category.application.CategoryService;
import easyweb.easywebservice.domain.Category.dto.CategoryDTO;
import easyweb.easywebservice.domain.Category.dto.CategoryDTO.CategoryCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리", description = "카테고리 관련 API 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록 API", description = "카테고리 등록 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 생성 성공시", content = @Content(schema = @Schema(implementation = CategoryCreateDto.class)))
    })
    @PostMapping
    public ResponseEntity<CategoryCreateDto> saveCategory(@RequestBody CategoryCreateDto categoryCreateDto) {

        return ResponseEntity.ok(categoryService.saveCategory(categoryCreateDto));
    }
}
