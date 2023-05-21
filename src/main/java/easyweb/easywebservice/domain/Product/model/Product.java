package easyweb.easywebservice.domain.Product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    /*
    추가해야될 데이터
    제조사
    제품 사진
    배송사
    제품 상세 정보용 사진 2장
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private int price;

    @Builder
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(int price) {
        this.price = price;
    }
}
