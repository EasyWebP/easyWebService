package easyweb.easywebservice.domain.Product.dto;

import java.util.List;
import java.util.stream.Collectors;

import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.model.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDTO {
    @Schema(description = "제품명")
    private String name;
    @Schema(description = "제품 가격")
    private int price;
    @Schema(description = "제조사")
    private String manufacturer;
    @Schema(description = "배송사")
    private String shippingCompany;
    @Schema(description = "제품 사진")
    private List<ProductImageDTO> productImages;

    @Builder
    public ProductDTO(String name, int price, String manufacturer, String shippingCompany,
            List<ProductImageDTO> productImages) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.shippingCompany = shippingCompany;
        this.productImages = productImages;
    }

    public Product toEntity() {
        List<ProductImage> productImageEntities = productImages.stream()
                .map(ProductImageDTO::toEntity)
                .collect(Collectors.toList());

        return Product.builder()
                .name(this.name)
                .price(this.price)
                .manufacturer(this.manufacturer)
                .shippingCompany(this.shippingCompany)
                .productImages(productImageEntities)
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 생성 요청 dto")
    public static class ProductCreateDto {
        @Schema(description = "제품명")
        private String name;
        @Schema(description = "제품 가격")
        private int price;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 생성 요청 dto")
    public static class ProductInfoDto {
        @Schema(description = "제품명")
        private String name;
        @Schema(description = "제품 가격")
        private int price;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 업데이트 요청 dto")
    public static class ProductUpdateDto {
        @Schema(description = "제품명")
        private String name;
        @Schema(description = "제품 가격")
        private int price;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 삭제 요청 dto")
    public static class ProductDeleteDto {
        @Schema(description = "제품 아이디")
        private Long id;
    }
}
