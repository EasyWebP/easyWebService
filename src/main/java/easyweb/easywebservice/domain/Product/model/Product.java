package easyweb.easywebservice.domain.Product.model;

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
    private String imagePath;
    private String detailImageUrl1;
    private String detailImageUrl2;
    private boolean liked = false;
    private String category;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public enum ProductStatus {
        SALE_ONLY,
        SALE_AND_RENTAL,
        RENTAL_ONLY
    }

    @Builder
    public Product(String name, int price, String manufacturer, String shippingCompany, String imagePath,
            String detailImageUrl1, String detailImageUrl2, boolean liked, String category, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.shippingCompany = shippingCompany;
        this.imagePath = imagePath;
        this.detailImageUrl1 = detailImageUrl1;
        this.detailImageUrl2 = detailImageUrl2;
        this.liked = liked;
        this.category = category;
        this.status = status;
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

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updateDetailImageUrl1(String detailImageUrl1) {
        this.detailImageUrl1 = detailImageUrl1;
    }

    public void updateDetailImageUrl2(String detailImageUrl2) {
        this.detailImageUrl2 = detailImageUrl2;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public void updateStatus(ProductStatus status) {
        this.status = status;
    }
}
