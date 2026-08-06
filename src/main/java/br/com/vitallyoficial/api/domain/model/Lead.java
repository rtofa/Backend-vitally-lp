package br.com.vitallyoficial.api.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lead {

    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String message;
    private String city;
    private String state;
    private LeadType type;
    private LocalDateTime createdAt;
    private List<LeadItem> items;
    private RdSyncStatus rdSyncStatus;
    private String segment;


    private Lead(String name, String phone, String email, String message, String city, String state, LeadType type, List<LeadItem> items) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório");

        this.id = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.city = city;
        this.state = state;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.rdSyncStatus = RdSyncStatus.PENDING;

        validate();
    }

    private Lead(String name, String phone, String email, String message, String city, String state, LeadType type, List<LeadItem> items, String segment) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório");

        this.id = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.city = city;
        this.state = state;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.rdSyncStatus = RdSyncStatus.PENDING;
        this.segment = segment;

        validate();
    }


    public void addItem(LeadItem item) {
        if (this.type != LeadType.QUOTE) {
            throw new IllegalStateException("Apenas orçamentos podem ter itens.");
        }
        this.items.add(item);
    }

    private void validate(){
        if (type == LeadType.QUOTE && (items == null || items.isEmpty())){
            throw new IllegalArgumentException("Leads de orçamento (QUOTE) precisam ter produtos selecionados.");
        }
    }

    public static Lead createContact(String name, String phone, String email, String message, String city, String state) {
        return new Lead(name, phone, email, message, city, state, LeadType.CONTACT, null);
    }

    public static Lead createQuote(String name, String phone, String email, String message, String city, String state, List<LeadItem> items) {
        return new Lead(name, phone, email, message, city, state, LeadType.QUOTE, items);
    }

    public static Lead createWhatsapp(String name, String phone, String email, String city, String state, String segment) {
        return new Lead(name, phone, email, null, city, state, LeadType.WHATSAPP, null, segment);
    }

    public RdSyncStatus getRdSyncStatus() {
        return rdSyncStatus;
    }

    public void setRdSyncStatus(RdSyncStatus rdSyncStatus) {
        this.rdSyncStatus = rdSyncStatus;
    }

    public String getState() { return state; }

    public String getCity() { return city; }

    public UUID getId() {
        return id;
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

    public void setId(UUID id) { this.id = id; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getSegment() { return segment; }
    public void setSegment(String segment) { this.segment = segment; }
}
