package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.repository.CategoryRepository;

import br.com.vitallyoficial.api.presentation.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String name, String imageUrl) {
        Category category = Category.create(name, imageUrl);
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID id, String name, String imageUrl) {
        Category category = getCategoryById(id);
        category.updateInfo(name, imageUrl);
        return categoryRepository.save(category);
    }

    public void activateCategory(UUID id) {
        Category category = getCategoryById(id);
        category.activate();
        categoryRepository.save(category);
    }

    public void deactivateCategory(UUID id) {
        Category category = getCategoryById(id);
        category.deactivate();
        categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        getCategoryById(id);
        categoryRepository.delete(id);
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }

    public PageResult<Category> getCategories(String search, int page, int size) {
        return categoryRepository.findAllPaginated(search, page, size);
    }
}