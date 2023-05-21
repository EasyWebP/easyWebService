package easyweb.easywebservice.domain.Product.application;

import java.util.HashMap;
import java.util.List;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductInfoDto;
import easyweb.easywebservice.domain.Product.repository.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductCreateDTO;
import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductUpdateDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product addProduct(ProductCreateDTO productCreateDTO) {
        Product product = productCreateDTO.toEntity();
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));

        if (productUpdateDTO.getName() != null) {
            existingProduct.updateName(productUpdateDTO.getName());
        }
        if (productUpdateDTO.getPrice() != 0) {
            existingProduct.updatePrice(productUpdateDTO.getPrice());
        }
        if (productUpdateDTO.getManufacturer() != null) {
            existingProduct.updateManufacturer(productUpdateDTO.getManufacturer());
        }
        if (productUpdateDTO.getShippingCompany() != null) {
            existingProduct.updateShippingCompany(productUpdateDTO.getShippingCompany());
        }
        if (productUpdateDTO.getImagePath() != null) {
            existingProduct.updateImagePath(productUpdateDTO.getImagePath());
        }
        if (productUpdateDTO.getDetailImageUrl1() != null) {
            existingProduct.updateDetailImageUrl1(productUpdateDTO.getDetailImageUrl1());
        }
        if (productUpdateDTO.getDetailImageUrl2() != null) {
            existingProduct.updateDetailImageUrl2(productUpdateDTO.getDetailImageUrl2());
        }
//        if (productUpdateDTO.getCategory() != null) {
//            existingProduct.updateCategory(productUpdateDTO.getCategory());
//        }
        if (productUpdateDTO.getStatus() != null) {
            existingProduct.updateStatus(productUpdateDTO.getStatus());
        }

        return existingProduct;
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

    public Page<ProductInfoDto> getAllProducts(String status, String like, String price, String desc, String asc, Pageable pageable) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("like", like);
        map.put("price", price);
        map.put("desc", desc);
        map.put("asc", asc);
        map.put("offset", pageable.getOffset());
        map.put("pageSize", pageable.getPageSize());
        List<ProductInfoDto> product = productMapper.findProduct(map);
        List<Long> counted = productMapper.countQueryForProduct(map);
        return new PageImpl<>(product, pageable, counted.size());
    }
}
