package easyweb.easywebservice.domain.Product.dto;

import easyweb.easywebservice.domain.Product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDTO {
    // DTO로 id를 전달받지 않으니까 이건 빼도돼여
    private Long id;
    @Schema(description = "제품명")
    private String name;
    @Schema(description = "제품 가격")
    private int price;

    @Builder
    public ProductDTO(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 생성 요청 dto")
    public static class ProductCreateDto {

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 생성 요청 dto")
    public static class ProductInfoDto {

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 업데이트 요청 dto")
    public static class ProductUpdateDto {

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 삭제 요청 dto")
    public static class ProductDeleteDto {

    }
}
