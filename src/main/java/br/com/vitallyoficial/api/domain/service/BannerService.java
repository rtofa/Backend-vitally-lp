package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.Banner;
import br.com.vitallyoficial.api.domain.repository.BannerRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public Banner createBanner(String title, String desktopImageUrl, String mobileImageUrl, Integer displayOrder) {
        if (desktopImageUrl == null || desktopImageUrl.isBlank()) {
            throw new IllegalArgumentException("A URL da imagem desktop é obrigatória.");
        }
        if (mobileImageUrl == null || mobileImageUrl.isBlank()) {
            throw new IllegalArgumentException("A URL da imagem mobile é obrigatória.");
        }

        Banner newBanner = new Banner(title, desktopImageUrl, mobileImageUrl,  true, displayOrder);
        return bannerRepository.save(newBanner);
    }

    public void deleteBannerById(UUID id){

        bannerRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Banner não encontrado!"));


        bannerRepository.delete(id);
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banner findById(UUID id) {

        return bannerRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Banner não encontrado!"));

    }

    public Banner updateBanner(UUID id, String title, String desktopImageUrl, String mobileImageUrl, Integer displayOrder){
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banner não encontrado."));

        banner.updateInfo(title, desktopImageUrl, mobileImageUrl, displayOrder);

        return bannerRepository.save(banner);
    }

    public List<Banner> getActiveBanners() {
        return bannerRepository.findAllActive();
    }

    public void changeStatus(UUID id, boolean isActive) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banner não encontrado."));

        if (isActive) {
            banner.activate();
        } else {
            banner.deactivate();
        }

        bannerRepository.save(banner);
    }
}