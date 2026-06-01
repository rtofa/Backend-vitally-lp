package br.com.vitallyoficial.api.domain.model;

import java.util.UUID;

public class Category {

    private UUID id;
    private String name;
    private String imageUrl;
    private Boolean active;

    public Category(UUID id, String name, String imageUrl, Boolean active) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome da categoria é obrigatório");

        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.active = active != null ? active : true;
    }

    public static Category create(String name, String imageUrl) {
        return new Category(UUID.randomUUID(), name, imageUrl, true);
    }

    public static Category restore(UUID id, String name, String imageUrl, Boolean active) {
        return new Category(id, name, imageUrl, active);
    }

    public void updateInfo(String name, String imageUrl) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrl = imageUrl;
        }
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getActive() {
        return active;
    }
}