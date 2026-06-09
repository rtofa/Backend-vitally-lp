package br.com.vitallyoficial.api.domain.event;

import java.util.UUID;

public record LeadCreatedEvent(
        UUID leadId,
        String name,
        String email,
        String phone
) {}
