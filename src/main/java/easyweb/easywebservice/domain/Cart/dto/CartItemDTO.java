package easyweb.easywebservice.domain.Cart.dto;

import easyweb.easywebservice.domain.Cart.model.CartItem;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.model.Product.ProductStatus;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "카트 아이템 생성 요청 DTO")
    public static class CartItemCreateDTO {
        @Schema(description = "제품 아이디")
        private Long productId;
        @Schema(description = "주문할 제품 개수")
        private int count;

        @Builder
        public CartItem toEntity(ProductRepository productRepository) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            return CartItem.builder()
                    .product(product)
                    .count(count)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "카트 아이템 정보 요청 DTO")
    public static class CartItemInfoDTO {
        @Schema(description = "카트 아이템 아이디")
        private Long cartItemId;
        @Schema(description = "제품 아이디")
        private Long productId;
        @Schema(description = "주문할 제품 개수")
        private int count;
        @Schema(description = "제품 이름")
        private String productName;
        @Schema(description = "제품 가격")
        private int price;
        @Schema(description = "제조사")
        private String manufacturer;
        @Schema(description = "이미지 경로")
        private String imagePath;
        @Schema(description = "제품 상태")
        private ProductStatus status;
    }
}
