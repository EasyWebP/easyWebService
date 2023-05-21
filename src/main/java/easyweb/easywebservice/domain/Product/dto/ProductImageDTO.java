package easyweb.easywebservice.domain.Product.dto;

import easyweb.easywebservice.domain.Product.model.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImageDTO {
    @Schema(description = "제품 사진")
    private String imagePath;
    @Schema(description = "제품 상세 사진1")
    private String detailImageUrl1;
    @Schema(description = "제품 상세 사진2")
    private String detailImageUrl2;

    @Builder
    public ProductImageDTO(String imagePath, String detailImageUrl1, String detailImageUrl2) {
        this.imagePath = imagePath;
        this.detailImageUrl1 = detailImageUrl1;
        this.detailImageUrl2 = detailImageUrl2;
    }

    public ProductImage toEntity() {
        return ProductImage.builder()
                .imagePath(this.imagePath)
                .detailImageUrl1(this.detailImageUrl1)
                .detailImageUrl2(this.detailImageUrl2)
                .build();
    }
}
