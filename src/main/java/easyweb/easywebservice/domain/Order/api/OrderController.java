package easyweb.easywebservice.domain.Order.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyweb.easywebservice.domain.Order.application.OrderService;
import easyweb.easywebservice.domain.Order.dto.OrderDTO.OrderCreateDTO;
import easyweb.easywebservice.domain.Order.dto.OrderDTO.OrderDirectCreateDTO;
import easyweb.easywebservice.domain.Order.dto.OrderDTO.OrderInfoDTO;
import easyweb.easywebservice.domain.Order.dto.OrderDTO.OrderItemInfoDTO;
import easyweb.easywebservice.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "주문", description = "주문 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문을 생성하는 API입니다")
    @PostMapping
    public ResponseEntity<OrderInfoDTO> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Long orderId = orderService.createOrder(memberId, orderCreateDTO);
        OrderInfoDTO orderInfoDTO = orderService.getOrderInfo(orderId);
        return ResponseEntity.ok(orderInfoDTO);
    }

    @Operation(summary = "바로 주문 생성", description = "한 제품에 대한 주문을 바로 생성하는 API입니다")
    @PostMapping("{productId}")
    public ResponseEntity<OrderInfoDTO> createDirectOrder(@PathVariable Long productId,
            @RequestBody OrderDirectCreateDTO orderDirectCreateDTO) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Long orderId = orderService.createDirectOrder(productId, memberId, orderDirectCreateDTO);
        OrderInfoDTO orderInfoDTO = orderService.getOrderInfo(orderId);
        return ResponseEntity.ok(orderInfoDTO);
    }

    @Operation(summary = "모든 주문 제품 조회", description = "모든 주문 제품 정보를 반환하는 API입니다")
    @GetMapping
    public ResponseEntity<List<OrderItemInfoDTO>> getOrderItemInfo() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<OrderItemInfoDTO> orderItemInfoDTOs = orderService.getOrderItemInfo(memberId);
        return ResponseEntity.ok(orderItemInfoDTOs);
    }
}
