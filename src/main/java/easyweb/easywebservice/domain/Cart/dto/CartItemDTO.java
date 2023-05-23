package easyweb.easywebservice.domain.Cart.dto;

import easyweb.easywebservice.domain.Cart.model.CartItem;
import easyweb.easywebservice.domain.Product.model.Product;
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
        private Long productId;
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
        private Long id;
        private Long productId;
        private int count;

        public static CartItemInfoDTO fromCartItem(CartItem cartItem) {
            return new CartItemInfoDTO(cartItem.getId(), cartItem.getProduct().getId(), cartItem.getCount());
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "카트 아이템 삭제 요청 DTO")
    public static class CartItemDeleteDTO {
        private Long itemId;
        private Long cartId;

        public void updateItemId(Long itemId) {
            this.itemId = itemId;
        }

        public void updateCartId(Long cartId) {
            this.cartId = cartId;
        }
    }
}