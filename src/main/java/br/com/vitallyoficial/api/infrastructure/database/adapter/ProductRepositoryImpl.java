package br.com.vitallyoficial.api.infrastructure.database.adapter;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.model.Product;
import br.com.vitallyoficial.api.domain.repository.ProductRepository;
import br.com.vitallyoficial.api.infrastructure.entity.CategoryEntity;
import br.com.vitallyoficial.api.infrastructure.entity.ProductEntity;
import br.com.vitallyoficial.api.infrastructure.database.repository.ProductJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class  ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;


    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) { this.productJpaRepository = productJpaRepository;}


    @Override
    public Product save(Product product) {

        ProductEntity entityToSave = toEntity(product);

        ProductEntity savedEntity = productJpaRepository.save(entityToSave);

        return toDomain(savedEntity);
    }

    @Override
    public void delete(UUID id) { productJpaRepository.deleteById(id); }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public PageResult<Product> findAllPaginated(String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> entityPage = productJpaRepository.findBySearchCriteria(search, pageable);

        List<Product> domainProducts = entityPage.getContent().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());


        return new PageResult<>(
                domainProducts,
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements(),
                entityPage.getSize()
        );
    }

    private ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();

        if (product.getId() != null) {
            entity.setId(product.getId());
        }

        if (product.getCategory() != null) {
            CategoryEntity catEntity = new CategoryEntity();
            catEntity.setId(product.getCategory().getId());
            entity.setCategoryEntity(catEntity);
        }

        entity.setName(product.getProductName());
        entity.setDescription(product.getProductDescription());
        entity.setImageUrl(product.getImageUrl());
        entity.setPrice(product.getPrice());
        entity.setDisplayOrder(product.getDisplayOrder());
        entity.setIsActive(product.getActive());

        return entity;
    }

    private Product toDomain(ProductEntity entity) {

        Category categoryDomain = entity.getCategoryEntity() != null ?
                Category.restore(
                        entity.getCategoryEntity().getId(),
                        entity.getCategoryEntity().getName(),
                        entity.getCategoryEntity().getImageUrl(),
                        entity.getCategoryEntity().getIsActive()
                ) : null;

        return Product.restore(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getDisplayOrder(),
                entity.getIsActive(),
                categoryDomain
        );
    }
}