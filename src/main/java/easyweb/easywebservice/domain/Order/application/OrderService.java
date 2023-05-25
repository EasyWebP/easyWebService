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
import easyweb.easywebservice.domain.Order.model.OrderBase;
import easyweb.easywebservice.domain.Order.model.OrderItem;
import easyweb.easywebservice.domain.Order.repository.OrderItemRepository;
import easyweb.easywebservice.domain.Order.repository.OrderRepository;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
        private final MemberRepository memberRepository;
        private final OrderRepository orderRepository;
        private final CartItemRepository cartItemRepository;
        private final OrderItemRepository orderItemRepository;
        private final ProductRepository productRepository;

        @Transactional
        public Long createOrder(Long memberId, OrderCreateDTO orderCreateDTO) {
                Member member = memberRepository.findById(memberId)
                                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

                OrderBase order = orderCreateDTO.toEntity(member);

                Cart cart = member.getCart();
                List<CartItem> cartItems = cart.getCartItems();
                List<OrderItem> orderItems = new ArrayList<>();

                for (CartItem cartItem : cartItems) {
                        OrderItem orderItem = OrderItem.builder()
                                        .product(cartItem.getProduct())
                                        .count(cartItem.getCount())
                                        .build();

                        orderItem.setOrder(order);
                        orderItems.add(orderItem);
                }

                order.setOrderItems(orderItems);

                orderRepository.save(order);
                cartItemRepository.deleteAll(cartItems);

                orderItemRepository.saveAll(orderItems);

                return order.getId();
        }

        @Transactional
        public Long createDirectOrder(Long productId, Long memberId, OrderDirectCreateDTO orderDirectCreateDTO) {
                Member member = memberRepository.findById(memberId)
                                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

                OrderBase order = orderDirectCreateDTO.toEntity(member);

                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                OrderItem orderItem = OrderItem.builder()
                                .product(product)
                                .count(order.getCount())
                                .build();

                orderItem.setOrder(order);

                orderRepository.save(order);
                orderItemRepository.save(orderItem);

                return order.getId();
        }

        @Transactional
        public OrderInfoDTO getOrderInfo(Long orderId) {
                OrderBase order = orderRepository.findById(orderId)
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
        public List<OrderItemInfoDTO> getOrderItemInfo(Long memberId) {
                Member member = memberRepository.findById(memberId)
                                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

                List<OrderBase> orders = member.getOrders();

                List<OrderItemInfoDTO> orderItemInfoDTOs = new ArrayList<>();

                for (OrderBase order : orders) {
                        List<OrderItem> orderItems = order.getOrderItems();

                        for (OrderItem orderItem : orderItems) {
                                Product product = orderItem.getProduct();

                                OrderItemInfoDTO orderItemInfoDTO = OrderItemInfoDTO.builder()
                                                .imagePath(product.getImagePath())
                                                .productName(product.getName())
                                                .manufacturer(product.getManufacturer())
                                                .price(product.getPrice())
                                                .status(product.getStatus())
                                                .orderDate(order.getOrderDate())
                                                .orderNumber(order.getOrderNumber())
                                                .count(orderItem.getCount())
                                                .build();

                                orderItemInfoDTOs.add(orderItemInfoDTO);
                        }
                }

                return orderItemInfoDTOs;
        }
}
