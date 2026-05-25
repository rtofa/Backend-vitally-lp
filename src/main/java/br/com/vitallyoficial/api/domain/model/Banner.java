package br.com.vitallyoficial.api.domain.model;

import java.util.UUID;

public class Banner {

    private UUID id;
    private String title;
    private String imageUrl;
    private Boolean isActive;
    private Integer displayOrder;


    public Banner(String title, String imageUrl, Boolean isActive, Integer displayOrder) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isActive = isActive != null ? isActive : true; // Por padrão, nasce ativo
        this.displayOrder = displayOrder;
    }

    public Banner(UUID id, String title, String imageUrl, Boolean isActive, Integer displayOrder) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.displayOrder = displayOrder;
    }

    public void updateInfo(String title, String imageUrl, Integer displayOrder) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrl = imageUrl;
        }
        if (displayOrder != null) {
            this.displayOrder = displayOrder;
        }
    }


    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }


    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public Boolean getIsActive() { return isActive; }
    public Integer getDisplayOrder() { return displayOrder; }


}