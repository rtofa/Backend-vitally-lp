package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.infrastructure.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BannerJpaRepository extends JpaRepository<BannerEntity, UUID> {
    List<BannerEntity> findByIsActiveTrueOrderByDisplayOrderAsc();
}