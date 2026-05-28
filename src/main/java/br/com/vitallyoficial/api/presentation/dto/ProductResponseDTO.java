package br.com.vitallyoficial.api.presentation.dto;

import br.com.vitallyoficial.api.domain.model.Product;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        Integer displayOrder,
        Boolean isActive
) {

    public static ProductResponseDTO fromDomain(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getDisplayOrder(),
                product.getActive()
        );
    }
}