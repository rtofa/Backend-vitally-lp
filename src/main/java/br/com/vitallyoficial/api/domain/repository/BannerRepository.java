package br.com.vitallyoficial.api.domain.repository;

import br.com.vitallyoficial.api.domain.model.Banner;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BannerRepository {
    Banner save(Banner banner);
    Optional<Banner> findById(UUID id);
    List<Banner> findAllActive();
    void delete(UUID id);
}