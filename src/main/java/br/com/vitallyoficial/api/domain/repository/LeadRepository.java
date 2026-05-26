package br.com.vitallyoficial.api.domain.repository;

import br.com.vitallyoficial.api.domain.model.Lead;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeadRepository {
    Lead save(Lead lead);
    Optional<Lead> findById(UUID id);
    List<Lead> findAll();
    void delete(UUID id);

}
