package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.LeadItem;
import br.com.vitallyoficial.api.domain.model.LeadType;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.repository.LeadRepository;
import br.com.vitallyoficial.api.presentation.dto.LeadItemResponseDTO;
import br.com.vitallyoficial.api.presentation.dto.LeadResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Lead createLead(String name, String phone, String email, String message, String city, String state, LeadType type, List<LeadItem> items) {
        Lead lead;

        if (type == LeadType.CONTACT) {
            lead = Lead.createContact(name, phone, email, message, city, state);
        } else {
            lead = Lead.createQuote(name, phone, email, message, city, state, items);
        }

        return leadRepository.save(lead);
    }

    public PageResult<LeadResponseDTO> findAll(Pageable pageable) {
        Page<Lead> leadsPage = leadRepository.findAll(pageable);

        List<LeadResponseDTO> dtos = leadsPage.getContent().stream()
                .map(lead -> new LeadResponseDTO(
                        lead.getId(),
                        lead.getName(),
                        lead.getEmail(),
                        lead.getPhone(),
                        lead.getMessage(),
                        lead.getCity(),
                        lead.getState(),
                        lead.getType(),
                        lead.getCreatedAt(),
                        lead.getItems().stream()
                                .map(item -> new LeadItemResponseDTO(
                                        item.getProductId(),
                                        item.getQuantity()
                                ))
                                .toList()

                ))
                    .toList();


        return new PageResult<>(
                dtos,
                leadsPage.getNumber(),
                leadsPage.getSize(),
                leadsPage.getTotalElements(),
                leadsPage.getTotalPages()
        );
    }

    public void deleteLeadById(UUID id){

        leadRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Lead não encontrado!"));


        leadRepository.delete(id);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }
}
