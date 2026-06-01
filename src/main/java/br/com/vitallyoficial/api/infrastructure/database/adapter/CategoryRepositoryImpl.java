package br.com.vitallyoficial.api.infrastructure.database.adapter;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.repository.CategoryRepository;
import br.com.vitallyoficial.api.infrastructure.database.repository.CategoryJpaRepository;
import br.com.vitallyoficial.api.infrastructure.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entityToSave = toEntity(category);
        CategoryEntity savedEntity = categoryJpaRepository.save(entityToSave);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return categoryJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public PageResult<Category> findAllPaginated(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> entityPage = categoryJpaRepository.findBySearchCriteria(search, pageable);

        List<Category> domainCategories = entityPage.getContent().stream()
                .map(this::toDomain)
                .toList();

        return new PageResult<>(
                domainCategories,
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements(),
                entityPage.getSize()
        );
    }

    @Override
    public void delete(UUID id) {
        categoryJpaRepository.deleteById(id);
    }

    private CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        entity.setImageUrl(category.getImageUrl());
        entity.setIsActive(category.getActive());
        return entity;
    }

    private Category toDomain(CategoryEntity entity) {
        return Category.restore(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getIsActive()
        );
    }
}