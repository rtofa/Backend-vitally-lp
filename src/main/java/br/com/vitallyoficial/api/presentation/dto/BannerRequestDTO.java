package br.com.vitallyoficial.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BannerRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String imageUrl,

        @NotNull(message = "A ordem de exibição é obrigatória")
        @Positive(message = "A ordem de exibição deve ser maior que zero")
        Integer displayOrder
) {}