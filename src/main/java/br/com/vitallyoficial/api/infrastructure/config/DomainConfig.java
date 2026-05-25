package br.com.vitallyoficial.api.infrastructure.config;

import br.com.vitallyoficial.api.domain.repository.BannerRepository;
import br.com.vitallyoficial.api.domain.service.BannerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public BannerService bannerService(BannerRepository bannerRepository) {
        return new BannerService(bannerRepository);
    }
}