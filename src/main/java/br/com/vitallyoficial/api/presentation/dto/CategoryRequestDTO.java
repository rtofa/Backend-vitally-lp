package br.com.vitallyoficial.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name,
        String imageUrl
) {}