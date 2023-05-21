package easyweb.easywebservice.domain.Product.repository;

import easyweb.easywebservice.domain.Product.dto.ProductDTO;
import easyweb.easywebservice.domain.Product.dto.ProductInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductInfoDto> findProduct(HashMap<String, Object> map);

    List<Long> countQueryForProduct(HashMap<String, Object> map);
}
