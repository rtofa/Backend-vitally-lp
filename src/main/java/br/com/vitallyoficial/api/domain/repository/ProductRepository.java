package br.com.vitallyoficial.api.domain.repository;


import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    PageResult<Product> findAllPaginated(String search, int page, int size);

    void delete(UUID id);
}
