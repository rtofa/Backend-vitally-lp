package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.LeadItem;
import br.com.vitallyoficial.api.domain.model.LeadType;
import br.com.vitallyoficial.api.domain.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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


    public void deleteLeadById(UUID id){

        leadRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Lead não encontrado!"));


        leadRepository.delete(id);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }
}
