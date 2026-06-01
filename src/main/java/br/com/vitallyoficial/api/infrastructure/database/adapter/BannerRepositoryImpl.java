package br.com.vitallyoficial.api.infrastructure.database.adapter;

import br.com.vitallyoficial.api.domain.model.Banner;
import br.com.vitallyoficial.api.domain.repository.BannerRepository;
import br.com.vitallyoficial.api.infrastructure.database.repository.BannerJpaRepository;
import br.com.vitallyoficial.api.infrastructure.entity.BannerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BannerRepositoryImpl implements BannerRepository {

    private final BannerJpaRepository jpaRepository;

    public BannerRepositoryImpl(BannerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Banner save(Banner banner) {
        BannerEntity entity = toEntity(banner);
        BannerEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Banner> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Banner> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Banner> findAllActive() {
        return jpaRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }


    private BannerEntity toEntity(Banner banner) {
        BannerEntity entity = new BannerEntity();
        entity.setId(banner.getId());
        entity.setTitle(banner.getTitle());
        entity.setDesktopImageUrl(banner.getDesktopImageUrl());
        entity.setMobileImageUrl(banner.getMobileImageUrl());
        entity.setIsActive(banner.getIsActive());
        entity.setDisplayOrder(banner.getDisplayOrder());
        return entity;
    }

    private Banner toDomain(BannerEntity entity) {
        return new Banner(
                entity.getId(),
                entity.getTitle(),
                entity.getDesktopImageUrl(),
                entity.getMobileImageUrl(),
                entity.getIsActive(),
                entity.getDisplayOrder()
        );
    }
}