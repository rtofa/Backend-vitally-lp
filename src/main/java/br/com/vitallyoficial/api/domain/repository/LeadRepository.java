package br.com.vitallyoficial.api.domain.repository;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.RdSyncStatus;
import br.com.vitallyoficial.api.infrastructure.entity.LeadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeadRepository {
    Lead save(Lead lead);
    Optional<Lead> findById(UUID id);
    List<Lead> findAll();
    void delete(UUID id);
    Page<Lead> findAll(Pageable pageable);
    List<Lead> findByRdSyncStatus(RdSyncStatus status);
}
