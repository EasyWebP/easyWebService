package easyweb.easywebservice.domain.Product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String imagePath;
    private String detailImageUrl1;
    private String detailImageUrl2;

    @Builder
    public ProductImage(String imagePath, String detailImageUrl1, String detailImageUrl2) {
        this.imagePath = imagePath;
        this.detailImageUrl1 = detailImageUrl1;
        this.detailImageUrl2 = detailImageUrl2;
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updatedetailImageUrl1(String detailImageUrl1) {
        this.detailImageUrl1 = detailImageUrl1;
    }

    public void updatedetailImageUrl2(String detailImageUrl2) {
        this.detailImageUrl2 = detailImageUrl2;
    }
}
