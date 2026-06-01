package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.Banner;
import java.util.UUID;

public record BannerResponseDTO(
        UUID id,
        String title,
        String desktopImageUrl,
        String mobileImageUrl,
        Boolean isActive,
        Integer displayOrder
) {

    public static BannerResponseDTO fromDomain(Banner banner) {
        return new BannerResponseDTO(
                banner.getId(),
                banner.getTitle(),
                banner.getDesktopImageUrl(),
                banner.getMobileImageUrl(),
                banner.getIsActive(),
                banner.getDisplayOrder()
        );
    }
}