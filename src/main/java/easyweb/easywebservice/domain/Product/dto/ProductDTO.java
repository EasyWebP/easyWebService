package easyweb.easywebservice.domain.Product.dto;

import easyweb.easywebservice.domain.Product.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private int price;

    @Builder
    public ProductDTO(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }
}
