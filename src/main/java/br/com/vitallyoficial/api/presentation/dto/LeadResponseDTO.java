package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.LeadItem;
import br.com.vitallyoficial.api.domain.model.LeadType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

    public record LeadResponseDTO(UUID id,
                              String name,
                              String phone,
                              String email,
                              String message,
                              String city,
                              String state,
                              LeadType type,
                              LocalDateTime createdAt,
                              List<LeadItemResponseDTO> items,
                              String segment
    ) {

    public static LeadResponseDTO fromDomain(Lead lead){

        List<LeadItemResponseDTO> itemDTOs = null;
        if (lead.getItems() != null) {
            itemDTOs = lead.getItems().stream()
                    .map(LeadItemResponseDTO::fromDomain)
                    .collect(java.util.stream.Collectors.toList());
        }

        return new LeadResponseDTO(
            lead.getId(),
            lead.getName(),
            lead.getPhone(),
            lead.getEmail(),
            lead.getMessage(),
            lead.getCity(),
            lead.getState(),
            lead.getType(),
            lead.getCreatedAt(),
            itemDTOs,
            lead.getSegment()
        );
    }
}
