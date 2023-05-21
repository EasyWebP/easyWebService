package easyweb.easywebservice.domain.Product.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import easyweb.easywebservice.domain.Product.application.ProductService;
import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import lombok.RequiredArgsConstructor;
@Tag(name = "제품 정보", description = "제품 정보 관련 API 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    /*
    지금은 리턴 타입 Product로 해놨는데, 이걸 이제 호출하는 API마다 리턴 타입 다 그거에 맞는 DTO 만들어주는게 좋아

    예를들어 메인페이지에서 드론 조회하는데, 메인페이지에서 보여주는 정보가

    드론명, 드론가격, 제품사진, 이정도이면,

    제조사, 제품 상세 정보용 사진 2장, 배송사 정보는 필요 없는거고

    Product에 나중에 뭐 연관관계 매핑때문에 데이터 더 붙으면 그냥 Product 리턴하면
    데이터 어마어마하게 많이 리턴될거야

    그래서 데이터 내뱉을때는 API 별로 그거에 맞는 DTO 리턴해주는게 좋아여

    dto 클래스들은 매우 많아질 수 있어서 ProductDTO 클래스안에 스태틱 클래스로 주로 만들어놔

    예시로 빈 dto 클래스 몇개 만들어놨으니까 보고 활용 ㄱ
     */

    // 제품 등록
    @Operation(summary = "제품 등록 API", description = "제품 등록 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 등록 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // 제품 수정
    @Operation(summary = "제품 수정 API", description = "제품 수정 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 수정 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);

        if (updatedProduct != null) {
            return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    리턴 타입을 스트링으로라도 넣어주는게 좋을 것 같아
    삭제 된 경우에는 DELETED 이런거 넣어주면 프론트가 삭제된걸 아니까 좋을 것 같아여
     */
    // 제품 삭제
    @Operation(summary = "제품 삭제 API", description = "제품 삭제 API 입니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*
    전체 조회에는 페이징이라는게 붙어야합니당
    페이징이 안 붙으면 상품 100개면 제품 조회 API가 불리면 상품 100개 모두 다 리턴되고, 1000개면 1000개, 100000개면 100000개
    리턴되는데,

    전체 데이터가 필요한 경우는 거의 없으니까 전체 데이터의 일부를 나눠서 보내주는데 그게 페이징입니당

    그거 한번 찾아보고 붙여야돼영
     */
    // 제품 전체 조회
    @Operation(summary = "제품 조회 API", description = "제품 조회 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 조회 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
