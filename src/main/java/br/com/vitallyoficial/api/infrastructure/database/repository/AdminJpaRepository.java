package br.com.vitallyoficial.api.infrastructure.database.repository;

import br.com.vitallyoficial.api.infrastructure.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminJpaRepository extends JpaRepository<AdminEntity, UUID> {

    Optional<AdminEntity> findByEmail(String email);

}