package easyweb.easywebservice.domain.Product.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductImageDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.model.ProductImage;
import easyweb.easywebservice.domain.Product.repository.ProductImageRepository;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public Product addProduct(ProductDTO productDTO) {
        /*
        productImage 저장되는 부분이 없어여

         */
        Product product = productDTO.toEntity();
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));
        /*
        여기서는 existingProduct null 체크 안해줘도돼

        null이면 위에서 선언한 IllegalArugmentException이 터질거야
         */
        if (existingProduct != null) {
            if (productDTO.getName() != null) {
                existingProduct.updateName(productDTO.getName());
            }
            if (productDTO.getPrice() != 0) {
                existingProduct.updatePrice(productDTO.getPrice());
            }
            if (productDTO.getManufacturer() != null) {
                existingProduct.updateManufacturer(productDTO.getManufacturer());
            }
            if (productDTO.getShippingCompany() != null) {
                existingProduct.updateShippingCompany(productDTO.getShippingCompany());
            }
            if (productDTO.getProductImages() != null) {
                for (ProductImageDTO productImageDTO : productDTO.getProductImages()) {
                    ProductImage existingProductImage = existingProduct.getProductImages().stream()
                            .filter(pi -> pi.getImagePath().equals(productImageDTO.getImagePath()))
                            .findFirst()
                            .orElse(null);

                    if (existingProductImage != null) {
                        if (productImageDTO.getImagePath() != null) {
                            existingProductImage.updateImagePath(productImageDTO.getImagePath());
                        }
                        if (productImageDTO.getDetailImageUrl1() != null) {
                            existingProductImage.updateDetailImageUrl1(productImageDTO.getDetailImageUrl1());
                        }
                        if (productImageDTO.getDetailImageUrl2() != null) {
                            existingProductImage.updateDetailImageUrl2(productImageDTO.getDetailImageUrl2());
                        }
                    }
                }
            }
            /*
            JPA에서는 가져온 엔티티의 값을 업데이트하고 나서 또 저장 안해줘도돼

            알아서 변경된 값 감지해서 데이터베이스 값 변경해줘
             */
            return productRepository.save(existingProduct);
        }

        return null;
    }

    // List<ProductImage> productImages = new ArrayList<>();
    // for (ProductImageDTO productImageDTO : productDTO.getProductImages()) {
    // ProductImage existingProductImage = productImageRepository
    // .findByImagePath(productImageDTO.getImagePath());

    // if (existingProductImage != null) {
    // existingProductImage.updateImagePath(productDTO.get); // 원하는 필드 업데이트를 어떻게
    // 접근하지
    // productImages.add(existingProductImage);
    // }
    // }

    public boolean deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));

        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }

        return false;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}