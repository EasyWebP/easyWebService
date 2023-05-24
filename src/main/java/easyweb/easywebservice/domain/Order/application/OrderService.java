package easyweb.easywebservice.domain.Order.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Cart.model.Cart;
import easyweb.easywebservice.domain.Cart.model.CartItem;
import easyweb.easywebservice.domain.Cart.repository.CartItemRepository;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Order.dto.OrderDTO.*;
import easyweb.easywebservice.domain.Order.model.Order;
import easyweb.easywebservice.domain.Order.repository.OrderRepository;
import easyweb.easywebservice.domain.Product.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private MemberRepository memberRepository;
    private OrderRepository orderRepository;
    private CartItemRepository cartItemRepository;

    @Transactional
    public void createOrder(Long memberId, OrderCreateDTO orderCreateDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Order order = orderCreateDTO.toEntity(member);

        Cart cart = member.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            order.addCartItem(cartItem);
        }

        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);
    }

    @Transactional
    public OrderInfoDTO getOrderInfo(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Member member = order.getMember();

        return OrderInfoDTO.builder()
                .memberName(member.getUsername())
                .orderDate(order.getOrderDate())
                .orderNumber(order.getOrderNumber())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .build();
    }

    @Transactional
    public List<CheckOrderInfoDTO> getCheckOrderInfo(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<CartItem> cartItems = order.getCartItems();

        List<CheckOrderInfoDTO> checkOrderInfoDTOs = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            CheckOrderInfoDTO checkOrderInfoDTO = CheckOrderInfoDTO.builder()
                    .imagePath(product.getImagePath())
                    .productName(product.getName())
                    .orderDate(order.getOrderDate())
                    .orderNumber(order.getOrderNumber())
                    .count(cartItem.getCount())
                    .build();

            checkOrderInfoDTOs.add(checkOrderInfoDTO);
        }

        return checkOrderInfoDTOs;
    }
}
