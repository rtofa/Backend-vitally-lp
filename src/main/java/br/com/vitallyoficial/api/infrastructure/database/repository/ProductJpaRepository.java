package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.infrastructure.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    // Query que busca ignorando maiúsculas/minúsculas
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND p.isActive = true")
    Page<ProductEntity> findBySearchCriteria(@Param("search") String search, Pageable pageable);
}