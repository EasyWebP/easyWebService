package easyweb.easywebservice.domain.Cart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Cart.dto.CartDTO.CartCreateDTO;
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
@Transactional
@Service
public class CartService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 카트에 제품 추가
    public Cart addItemToCart(CartCreateDTO cartCreateDTO, List<CartItemCreateDTO> cartItemCreateDTOs) {
        Member member = memberRepository.findById(cartCreateDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Cart cart = member.getCart();
        /*
         * 아래 cart 새로 저장하는 부분은 빼야될 것 같아 대신 회원가입하면서 cart 객체 생성해줄게 그거 불러오면될듯?
         */
        if (cart == null) {
            cart = cartCreateDTO.toEntity(memberRepository);
            cart.updateCount(0);
            cart.updateMember(member);
            cartRepository.save(cart);
        }

        for (CartItemCreateDTO cartItemCreateDTO : cartItemCreateDTOs) {
            Product product = productRepository.findById(cartItemCreateDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            /*
             * 상품조회가 중복되는 것 같아 그냥
             * CartItem.builder.product(product).cart(cart).count(cartItemCreateDto.getCount
             * ()).build;
             * 로 CartItem을 생성해도되지 않을까?
             */
            CartItem cartItem = CartItem.builder().product(product).cart(cart).count(cartItemCreateDTO.getCount())
                    .build();

            cartItemRepository.save(cartItem);
            /*
             * Cart에 장바구니 총 아이템 수를 계산할 count가 없어도 장바구니 아이템 총 개수를 계산할 수 있다는 놀라운 사실
             * 
             * 그렇기 때문에 아래 코드는 사실 불필요합니당
             */
            cart.updateCount(cart.getCount() + cartItemCreateDTO.getCount());
        }

        return cart;
    }

    /*
     * 아래 코드도 잘했네
     * 
     * 아래 코드가 가독성이 안좋다고 느껴지거나,
     * 뭔가 코드 짜면서 아 이거 좀 아닌 것 같은데, 더 편하게 할 수 있을 것 같은데
     * 라는 생각이 들었다면
     * 
     * https://ssdragon.tistory.com/97
     * JPA DTO 직접 조회를 찾아보세영
     */
    // 카트 안의 제품들 조회
    public List<CartItemInfoDTO> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        List<CartItem> cartItems = cart.getCartItems();

        List<CartItemInfoDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            cartItemDTOs.add(CartItemInfoDTO.fromCartItem(cartItem));
        }

        return cartItemDTOs;
    }

    // 카트 안의 제품 삭제
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
