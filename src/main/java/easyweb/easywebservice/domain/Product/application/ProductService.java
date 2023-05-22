package easyweb.easywebservice.domain.Product.application;

import java.util.HashMap;
import java.util.List;

import easyweb.easywebservice.domain.Category.exception.CategoryNotFoundException;
import easyweb.easywebservice.domain.Category.model.Category;
import easyweb.easywebservice.domain.Category.repository.CategoryRepository;
import easyweb.easywebservice.domain.Like.dto.LikeDTO;
import easyweb.easywebservice.domain.Like.dto.LikeDTO.LikeCreateDto;
import easyweb.easywebservice.domain.Like.dto.LikeDTO.LikeDeleteDto;
import easyweb.easywebservice.domain.Like.exception.LikeAlreadyExists;
import easyweb.easywebservice.domain.Like.exception.LikeDoesntExist;
import easyweb.easywebservice.domain.Like.model.Like;
import easyweb.easywebservice.domain.Like.repository.LikeRepository;
import easyweb.easywebservice.domain.Member.model.Member;
import easyweb.easywebservice.domain.Member.repository.MemberRepository;
import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductDetailDto;
import easyweb.easywebservice.domain.Product.dto.ProductInfoDto;
import easyweb.easywebservice.domain.Product.repository.ProductMapper;
import easyweb.easywebservice.global.error.exception.NotFoundByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductCreateDTO;
import easyweb.easywebservice.domain.Product.dto.ProductDTO.ProductUpdateDTO;
import easyweb.easywebservice.domain.Product.model.Product;
import easyweb.easywebservice.domain.Product.model.Product.ProductStatus;
import easyweb.easywebservice.domain.Product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Product addProduct(ProductCreateDTO productCreateDTO) {
        Category category = categoryRepository.findCategoryByName(productCreateDTO.getCategory()).orElseThrow(CategoryNotFoundException::new);
        Product product = productCreateDTO.toEntity(category);
        return productRepository.save(product);
    }

    @Transactional
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
        if (productUpdateDTO.getCategory() != null) {
            Category category = categoryRepository.findCategoryByName(productUpdateDTO.getCategory()).orElseThrow(CategoryNotFoundException::new);
            existingProduct.updateCategory(category);
        }
        if (productUpdateDTO.getStatus() != null) {
            ProductStatus status = null;
            for (ProductStatus value : ProductStatus.values()) {
                if (value == productUpdateDTO.getStatus()) {
                    status = value;
                    break;
                }
            }

            if (status != null) {
                existingProduct.updateStatus(status);
            } else {
                throw new IllegalArgumentException("Invalid status value");
            }
        }

        return existingProduct;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));

        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }

        return false;
    }

    @Transactional
    public Page<ProductInfoDto> getAllProducts(String status, String like, String asc, String category, Pageable pageable) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("like", like);
        map.put("asc", asc);
        map.put("category", category);
        map.put("offset", pageable.getOffset());
        map.put("pageSize", pageable.getPageSize());
        List<ProductInfoDto> product = productMapper.findProduct(map);
        List<Long> counted = productMapper.countQueryForProduct(map);
        return new PageImpl<>(product, pageable, counted.size());
    }

    @Transactional
    public ProductDetailDto getProductDetail(Long memberId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NotFoundByIdException::new);
        boolean liked = likeRepository.existsByMemberIdAndProductId(memberId, productId);
        Category category = product.getCategory();
        return ProductDetailDto.builder()
                .id(productId)
                .name(product.getName())
                .price(product.getPrice())
                .imagePath(product.getImagePath())
                .liked(liked)
                .shippingCompany(product.getShippingCompany())
                .detailImageUrl1(product.getDetailImageUrl1())
                .detailImageUrl2(product.getDetailImageUrl2())
                .manufacturer(product.getManufacturer())
                .status(product.getStatus())
                .category(category.getName())
                .build();
    }

    @Transactional
    public Page<ProductInfoDto> getLikedProducts(Long memberId, Pageable pageable) {

        return productRepository.findLikedProducts(memberId, pageable);
    }

    @Transactional
    public Like addLikeToProduct(Long memberId, LikeCreateDto likeCreateDto) {
        if (likeRepository.existsByMemberIdAndProductId(memberId, likeCreateDto.getProductId())) {
            throw new LikeAlreadyExists();
        }
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundByIdException::new);
        Product product = productRepository.findById(likeCreateDto.getProductId()).orElseThrow(NotFoundByIdException::new);
        Like build = Like.builder().member(member).product(product).build();
        return likeRepository.save(build);

    }

    @Transactional
    public LikeDeleteDto deleteLike(Long memberId, LikeDeleteDto likeDeleteDto) {
        if (likeRepository.existsByMemberIdAndProductId(memberId, likeDeleteDto.getProductId())) {
            likeRepository.deleteByMemberIdAndProductId(memberId, likeDeleteDto.getProductId());
        } else {
            throw new LikeDoesntExist();
        }
        return likeDeleteDto;
    }
}
