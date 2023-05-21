package easyweb.easywebservice.domain.Product.api;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import easyweb.easywebservice.domain.Product.application.ProductService;
import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductCreateDTO;
import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductUpdateDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import lombok.RequiredArgsConstructor;

@Tag(name = "제품 정보", description = "제품 정보 관련 API 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    // 제품 등록
    @Operation(summary = "제품 등록 API", description = "제품 등록 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 등록 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product createdProduct = productService.addProduct(productCreateDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // 제품 수정
    @Operation(summary = "제품 수정 API", description = "제품 수정 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 수정 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
            @RequestBody ProductUpdateDTO productUpdateDTO) {
        Product updatedProduct = productService.updateProduct(id, productUpdateDTO);

        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    // 제품 삭제
    @Operation(summary = "제품 삭제 API", description = "제품 삭제 API 입니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.NO_CONTENT);
    }

    // 제품 조회 products?page=0&size=10
    @Operation(summary = "제품 조회 API", description = "제품 조회 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 조회 성공시", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @GetMapping
    public Page<ProductInfoDto> getAllProducts(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "like", required = false) String like, @RequestParam(value = "price", required = false) String price, @RequestParam(value = "desc", required = false) String desc, @RequestParam(value = "asc", required = false) String asc, Pageable pageable) {

        return productService.getAllProducts(status, like, price, desc, asc, pageable);
    }
}
