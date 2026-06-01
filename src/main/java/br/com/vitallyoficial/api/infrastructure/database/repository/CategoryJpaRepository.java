package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.infrastructure.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {

    @Query("""
        SELECT c FROM CategoryEntity c
        WHERE (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<CategoryEntity> findBySearchCriteria(@Param("search") String search, Pageable pageable);
}