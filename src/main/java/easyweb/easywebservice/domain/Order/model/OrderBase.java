package easyweb.easywebservice.domain.Order.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import easyweb.easywebservice.domain.Member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private String phoneNumber;
    private String address;
    private int count;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Builder
    public OrderBase(String orderNumber, LocalDate orderDate, String phoneNumber, String address, Member member,
            int count) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.member = member;
        this.count = count;
    }
}
