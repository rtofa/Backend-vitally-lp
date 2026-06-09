package br.com.vitallyoficial.api.infrastructure.integration.rdstation;

import br.com.vitallyoficial.api.domain.event.LeadCreatedEvent;
import br.com.vitallyoficial.api.domain.model.RdSyncStatus;
import br.com.vitallyoficial.api.domain.repository.LeadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class RdStationIntegrationListener {

    private static final Logger log = LoggerFactory.getLogger(RdStationIntegrationListener.class);
    private final RestClient restClient;
    private final LeadRepository leadRepository;
    private static final String WEBHOOK_URL = "https://webhook.royalserver.com.br/webhook/formularios-site-2026-vitally";

    public RdStationIntegrationListener(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
        this.restClient = RestClient.builder().build();
    }

    @Async
    @EventListener
    @Transactional
    public void handleLeadCreatedEvent(LeadCreatedEvent event) {
        log.info("Iniciando envio assíncrono do Lead ID: {} para o Webhook", event.leadId());

        try {

            String payload = """
                {
                  "nome": "%s",
                  "email": "%s",
                  "telefone": "%s",
                  "identificador": "Site-Ryan"
                }
                """.formatted(event.name(), event.email(), event.phone());

            restClient.post()
                    .uri(WEBHOOK_URL)
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            updateLeadStatus(event.leadId(), RdSyncStatus.SYNCED);
            log.info("Lead ID: {} enviado com sucesso para o Webhook.", event.leadId());

        } catch (Exception e) {
            log.error("Erro ao enviar Lead ID: {}. Motivo: {}", event.leadId(), e.getMessage());
            updateLeadStatus(event.leadId(), RdSyncStatus.FAILED);
        }
    }

    private void updateLeadStatus(UUID leadId, RdSyncStatus status) {
        leadRepository.findById(leadId).ifPresent(lead -> {
            lead.setRdSyncStatus(status);
            leadRepository.save(lead);
        });
    }
}