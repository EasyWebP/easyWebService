package easyweb.easywebservice.domain.Product.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

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
