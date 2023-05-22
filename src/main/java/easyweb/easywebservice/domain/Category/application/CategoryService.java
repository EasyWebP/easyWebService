package easyweb.easywebservice.domain.Category.application;

import easyweb.easywebservice.domain.Category.dto.CategoryDTO;
import easyweb.easywebservice.domain.Category.dto.CategoryDTO.CategoryCreateDto;
import easyweb.easywebservice.domain.Category.exception.DuplicateCategoryException;
import easyweb.easywebservice.domain.Category.model.Category;
import easyweb.easywebservice.domain.Category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryCreateDto saveCategory(CategoryCreateDto categoryCreateDto) {
        if (categoryRepository.existsByName(categoryCreateDto.getName())) {
            throw new DuplicateCategoryException();
        }
        Category entity = categoryCreateDto.toEntity();
        categoryRepository.save(entity);

        return categoryCreateDto;
    }
}
