package easyweb.easywebservice.domain.Cart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemCreateDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemDeleteDTO;
import easyweb.easywebservice.domain.Cart.dto.CartItemDTO.CartItemInfoDTO;
import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.domain.Cart.model.CartItem;
import easyweb.easywebservice.domain.Cart.repository.CartItemRepository;
import easyweb.easywebservice.domain.Cart.repository.CartRepository;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 카트에 제품 추가
    @Transactional
    public void addItemToCart(Long memberId, CartItemCreateDTO cartItemCreateDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Cart cart = member.getCart();

        Product product = productRepository.findById(cartItemCreateDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem cartItem = CartItem.builder().product(product).cart(cart).count(cartItemCreateDTO.getCount())
                .build();
        cartItemRepository.save(cartItem);

        cart.updateCount(cart.getCount() + cartItemCreateDTO.getCount());
    }

    // 카트 안의 제품들 조회
    @Transactional
    public List<CartItemInfoDTO> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        List<CartItem> cartItems = cart.getCartItems();

        List<CartItemInfoDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemInfoDTO cartItemInfoDTO = CartItemInfoDTO.builder()
                    .cartItemId(cartItem.getId())
                    .productId(cartItem.getProduct().getId())
                    .count(cartItem.getCount())
                    .productName(cartItem.getProduct().getName())
                    .price(cartItem.getProduct().getPrice())
                    .manufacturer(cartItem.getProduct().getManufacturer())
                    .imagePath(cartItem.getProduct().getImagePath())
                    .status(cartItem.getProduct().getStatus())
                    .build();

            cartItemDTOs.add(cartItemInfoDTO);
        }

        return cartItemDTOs;
    }

    // 카트 안의 제품 삭제
    @Transactional
    public void deleteCartItem(CartItemDeleteDTO cartItemDeleteDTO) {
        CartItem cartItem = cartItemRepository.findById(cartItemDeleteDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cartItemDeleteDTO.getCartId())) {
            throw new IllegalArgumentException("Cart item does not belong to the provided cart ID");
        }

        Cart cart = cartItem.getCart();
        int cartItemCount = cartItem.getCount();
        cart.updateCount(cart.getCount() - cartItemCount);
        cartItemRepository.delete(cartItem);
    }

}
