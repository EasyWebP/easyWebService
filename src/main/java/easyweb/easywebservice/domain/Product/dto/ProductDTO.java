package easyweb.easywebservice.domain.Product.dto;

import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.model.Product.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class ProductDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 생성 요청 DTO")
    public static class ProductCreateDTO {
        @Schema(description = "제품명")
        private String name;
        @Schema(description = "제품 가격")
        private int price;
        @Schema(description = "제조사")
        private String manufacturer;
        @Schema(description = "배송사")
        private String shippingCompany;
        @Schema(description = "제품 사진 경로")
        private String imagePath;
        @Schema(description = "제품 상세 사진 Url 1")
        private String detailImageUrl1;
        @Schema(description = "제품 상세 사진 Url 2")
        private String detailImageUrl2;
        /*
        좋아요 여부는 빠져야될 것 같아 이건 나중에 좋아요 넣고 나서 ㄱㄱ
         */
        @Schema(description = "좋아요 여부")
        private boolean liked;
        @Schema(description = "카테고리")
        private String category;
        @Schema(description = "판매 상품 여부")
//        @Builder.Default
        private ProductStatus status;

        public Product toEntity() {
            return Product.builder()
                    .name(this.name)
                    .price(this.price)
                    .manufacturer(this.manufacturer)
                    .shippingCompany(this.shippingCompany)
                    .imagePath(imagePath)
                    .detailImageUrl1(detailImageUrl1)
                    .detailImageUrl2(detailImageUrl2)
//                    .liked(liked)
//                    .category(category)
                    .status(status)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "상품 업데이트 요청 DTO")
    public static class ProductUpdateDTO {
        @Schema(description = "제품명")
        private String name;
        @Schema(description = "제품 가격")
        private int price;
        @Schema(description = "제조사")
        private String manufacturer;
        @Schema(description = "배송사")
        private String shippingCompany;
        @Schema(description = "제품 사진 경로")
        private String imagePath;
        @Schema(description = "제품 상세 사진 Url 1")
        private String detailImageUrl1;
        @Schema(description = "제품 상세 사진 Url 2")
        private String detailImageUrl2;
        @Schema(description = "카테고리")
        private String category;
        @Schema(description = "판매 상품 여부")
        private ProductStatus status;
    }



}
