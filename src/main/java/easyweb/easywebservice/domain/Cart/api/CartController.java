package easyweb.easywebservice.domain.Cart.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyweb.easywebservice.domain.Cart.application.CartService;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemCreateDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemDeleteDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemInfoDTO;
import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "장바구니", description = "장바구니 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니에 아이템 추가", description = "장바구니에 아이템 추가 API 입니다")
    @PostMapping
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartItemCreateDTO cartItemCreateDTO) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Cart cart = cartService.addItemToCart(memberId, cartItemCreateDTO);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "장바구니에 있는 아이템 조회", description = "장바구니에 있는 모든 아이템 조회 API 입니다")
    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItemInfoDTO>> getCartItems(@PathVariable Long cartId) {
        List<CartItemInfoDTO> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @Operation(summary = "장바구니에 있는 아이템 삭제", description = "장바구니에 있는 아이템 삭제 API 입니다")
    @DeleteMapping
    public ResponseEntity<String> deleteCartItem(@RequestBody CartItemDeleteDTO cartItemDeleteDTO) {
        try {
            cartService.deleteCartItem(cartItemDeleteDTO);
            return ResponseEntity.ok("Cart item deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
