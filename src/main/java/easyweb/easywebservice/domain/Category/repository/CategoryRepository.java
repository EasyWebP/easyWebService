package easyweb.easywebservice.domain.Category.repository;

import easyweb.easywebservice.domain.Category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
