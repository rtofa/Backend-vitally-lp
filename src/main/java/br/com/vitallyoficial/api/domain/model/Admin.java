package br.com.vitallyoficial.api.domain.model;

import java.util.UUID;

public class Admin {

    private UUID id;
    private String email;
    private String password;

    private Admin(UUID id, String email, String password) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("O e-mail é obrigatório.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("A senha é obrigatória.");

        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Admin create(String email, String encodedPassword) {
        return new Admin(UUID.randomUUID(), email, encodedPassword);
    }

    public static Admin restore(UUID id, String email, String password) {
        return new Admin(id, email, password);
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}