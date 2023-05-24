package easyweb.easywebservice.domain.Product.dto;

import easyweb.easywebservice.domain.Product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@Schema(description = "상품 정보 요청 DTO")
public class ProductInfoDto {
    @Schema(description = "제품 아이디")
    private Long id;
    @Schema(description = "제품명")
    private String name;
    @Schema(description = "제품 가격")
    private int price;
    @Schema(description = "제조사")
    private String manufacturer;
    @Schema(description = "제품 사진 경로")
    private String imagePath;
    @Schema(description = "제품 상태")
    private String status;
    @Builder
    public ProductInfoDto(Long id, String name, int price, String manufacturer,  String imagePath, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.imagePath = imagePath;
        this.status = status;
    }
}
