package br.com.vitallyoficial.api.presentation.dto;

import jakarta.validation.constraints.Positive;

public record BannerUpdateDTO(
        String title,
        String imageUrl,
        @Positive(message = "A ordem de exibição deve ser maior que zero")
        Integer displayOrder
) {}