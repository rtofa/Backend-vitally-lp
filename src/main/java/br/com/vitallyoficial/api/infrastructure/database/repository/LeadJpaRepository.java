package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.infrastructure.entity.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeadJpaRepository extends JpaRepository<LeadEntity, UUID> {
}
