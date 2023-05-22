package easyweb.easywebservice.domain.Category.repository;

import easyweb.easywebservice.domain.Category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
    boolean existsByName(String name);
}
