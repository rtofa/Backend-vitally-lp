package br.com.vitallyoficial.api.domain;

import br.com.vitallyoficial.api.infraestructure.entity.LeadItemEntity;

import java.time.LocalDateTime;
import java.util.List;

public class Lead {

    private String name;
    private String phone;
    private String email;
    private String message;
    private LeadType type;
    private LocalDateTime createdAt;
    private List<LeadItem> items;


    public Lead(String name, String phone, String email, String message, LeadType type, LocalDateTime createdAt) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório");

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
    }

    // Regra de negócio isolada no domínio
    public void addItem(LeadItem item) {
        if (this.type != LeadType.QUOTE) {
            throw new IllegalStateException("Apenas orçamentos podem ter itens.");
        }
        this.items.add(item);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public LeadType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<LeadItem> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(LeadType type) {
        this.type = type;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(List<LeadItem> items) {
        this.items = items;
    }
}
