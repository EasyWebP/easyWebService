package easyweb.easywebservice.domain.Product.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));

        if (existingProduct != null) {
            if (productDTO.getName() != null) {
                existingProduct.updateName(productDTO.getName());
            }
            if (productDTO.getPrice() != 0) {
                existingProduct.updatePrice(productDTO.getPrice());
            }

            return productRepository.save(existingProduct);
        }

        return null;
    }

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
