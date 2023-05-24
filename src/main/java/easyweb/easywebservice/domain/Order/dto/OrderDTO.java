package easyweb.easywebservice.domain.Order.dto;

import java.time.LocalDate;
import java.util.Random;

import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Order.model.Order;
import easyweb.easywebservice.domain.common.Format;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "주문 생성 요청 DTO")
    public static class OrderCreateDTO {
        @Schema(description = "주문 일자")
        private LocalDate orderDate;
        @Schema(description = "주문 번호")
        private String orderNumber;
        @Schema(description = "연락처")
        private String phoneNumber;
        @Schema(description = "배송지 주소")
        private String address;

        public String getFormattedPhoneNumber() {
            return Format.phone(phoneNumber);
        }

        private String generateOrderNumber() {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 8; i++) {
                int digit = random.nextInt(10);
                sb.append(digit);
            }

            return sb.toString();
        }

        public Order toEntity(Member member) {
            orderDate = LocalDate.now();
            orderNumber = generateOrderNumber();

            return Order.builder()
                    .member(member)
                    .orderDate(orderDate)
                    .orderNumber(orderNumber)
                    .phoneNumber(Format.phone(phoneNumber))
                    .address(address)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "주문 정보 요청 DTO")
    public static class OrderInfoDTO {
        @Schema(description = "회원 이름")
        private String memberName;
        @Schema(description = "주문 일자")
        private LocalDate orderDate;
        @Schema(description = "주문 번호")
        private String orderNumber;
        @Schema(description = "연락처")
        private String phoneNumber;
        @Schema(description = "배송지 주소")
        private String address;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(description = "주문 정보 조회 요청 DTO")
    public static class CheckOrderInfoDTO {
        @Schema(description = "드론 이미지 경로")
        private String imagePath;
        @Schema(description = "제품 이름")
        private String productName;
        @Schema(description = "제조사")
        private String manufacturer;
        @Schema(description = "제품 가격")
        private int price;
        @Schema(description = "주문 수량")
        private int count;
        @Schema(description = "주문 일자")
        private LocalDate orderDate;
        @Schema(description = "주문 번호")
        private String orderNumber;
    }
}
