package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.LeadItem;
import java.util.UUID;

public record LeadItemResponseDTO(
        UUID productId,
        Integer quantity
) {
    public static LeadItemResponseDTO fromDomain(LeadItem item) {
        return new LeadItemResponseDTO(item.getProductId(), item.getQuantity());
    }
}