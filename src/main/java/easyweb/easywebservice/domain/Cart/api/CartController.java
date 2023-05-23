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

    /*
     * 장바구니에 상품 추가하는 API인 것 같고,
     * cartCreateDto는 장바구니 생성 dto인 것 같고,
     * cartItemCreateDto는 장바구니에 추가할 아이템들인 것 같네
     * 
     * 서비스에 짜놓은거 보니까 이 API가 호출될때마다 cart가 새로 저장되고,
     * 생성된 cart에 cartItem이 담기는 것 같은데
     * 
     * 일단 API가 호출될때마다 cart가 새로 저장되는게 문제일듯??
     * 
     * 엔티티 짜놓은거 보니까 @OneToOne으로 cart랑 member랑 일대일인데, 아래 API가 호출될때마다 cart가 새로 생기면
     * 
     * @OneToMany의 관계이지 않을까??
     * 
     * 장바구니 생성을 만들어놓은게 이걸 안만들면 장바구니 자체가 안생기니까 만들어 놓은 것 같은데
     * 이럴때는 회원가입 요청 들어올때, member랑 1대1로 장바구니하나 만들고, 장바구니 아이템 생성 요청이 오면
     * 그 장바구니에 아이템 추가하면될듯??
     */
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
