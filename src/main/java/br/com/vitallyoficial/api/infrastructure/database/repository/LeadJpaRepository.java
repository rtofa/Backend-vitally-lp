package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.domain.model.RdSyncStatus;
import br.com.vitallyoficial.api.infrastructure.entity.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeadJpaRepository extends JpaRepository<LeadEntity, UUID> {

    List<LeadEntity> findByRdSyncStatus(RdSyncStatus status);

}