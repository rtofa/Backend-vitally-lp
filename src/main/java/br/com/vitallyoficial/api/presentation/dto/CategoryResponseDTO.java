package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.Category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        String imageUrl,
        Boolean isActive
) {
    public static CategoryResponseDTO fromDomain(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getImageUrl(),
                category.getActive()
        );
    }
}