package br.com.vitallyoficial.api.domain.repository;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(UUID id);
    PageResult<Category> findAllPaginated(String search, int page, int size);
    void delete(UUID id);
}