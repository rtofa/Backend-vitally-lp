package br.com.vitallyoficial.api.domain.repository;

import br.com.vitallyoficial.api.domain.model.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findById(UUID id);
}