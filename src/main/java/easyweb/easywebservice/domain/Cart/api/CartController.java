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
import easyweb.easywebservice.domain.Cart.dto.CartDTO.CartCreateDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemCreateDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemDeleteDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemInfoDTO;
import easyweb.easywebservice.domain.Cart.model.Cart;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartCreateDTO cartCreateDTO,
            @RequestBody List<CartItemCreateDTO> cartItemCreateDTOs) {
        Cart cart = cartService.addItemToCart(cartCreateDTO, cartItemCreateDTOs);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}/cartItems")
    public ResponseEntity<List<CartItemInfoDTO>> getCartItems(@PathVariable Long cartId) {
        List<CartItemInfoDTO> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/cart/items")
    public ResponseEntity<String> deleteCartItem(@RequestBody CartItemDeleteDTO cartItemDeleteDTO) {
        try {
            cartService.deleteCartItem(cartItemDeleteDTO);
            return ResponseEntity.ok("Cart item deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
