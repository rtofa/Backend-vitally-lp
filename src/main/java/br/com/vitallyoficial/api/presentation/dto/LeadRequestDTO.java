package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.LeadType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LeadRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O telefone é obrigatório")
        String phone,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        String message,

        @NotBlank(message = "A cidade é obrigatória")
        String city,

        @NotBlank(message = "O estado é obrigatório")
        String state,

        @NotNull(message = "O tipo do lead é obrigatório")
        LeadType type,

        @Valid
        List<LeadItemRequestDTO> items,

        String segment
) {}