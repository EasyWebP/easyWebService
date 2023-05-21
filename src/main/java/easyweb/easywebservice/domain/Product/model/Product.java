package easyweb.easywebservice.domain.Product.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private int price;
    private String manufacturer;
    private String shippingCompany;
    /*
    아래처럼 어레이리스트 초기화하고, fetchType = FetchType.LAZY 꼭 써줘야돼
    오 근데 이렇게 쓰면 @OneToMany를 쓸 이유가 있을까?

    ProductImage안에
    상품 메인 사진, 상품 세부 이미지 2개 저장할 수 있는데

    이런 구조면 Product하나가 여러개의 ProductImage를 가질 수 있을 듯??
     */
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @Builder
    public Product(String name, int price, String manufacturer, String shippingCompany,
            List<ProductImage> productImages) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.shippingCompany = shippingCompany;
        this.productImages = productImages;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public void updateManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void updateShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
}
