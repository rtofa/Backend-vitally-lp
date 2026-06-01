package br.com.vitallyoficial.api.domain.model;

import java.util.UUID;

public class Banner {

    private UUID id;
    private String title;
    private String desktopImageUrl;
    private String mobileImageUrl;
    private Boolean isActive;
    private Integer displayOrder;


    public Banner(String title, String desktopImageUrl, String mobileImageUrl, Boolean isActive, Integer displayOrder) {
        this.title = title;
        this.desktopImageUrl = desktopImageUrl;
        this.mobileImageUrl = mobileImageUrl;
        this.isActive = isActive != null ? isActive : true;
        this.displayOrder = displayOrder;
    }

    public Banner(UUID id, String title, String desktopImageUrl, String mobileImageUrl, Boolean isActive, Integer displayOrder) {
        this.id = id;
        this.title = title;
        this.desktopImageUrl = desktopImageUrl;
        this.mobileImageUrl = mobileImageUrl;
        this.isActive = isActive;
        this.displayOrder = displayOrder;
    }

    public void updateInfo(String title, String desktopImageUrl, String mobileImageUrl, Integer displayOrder) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (desktopImageUrl != null && !desktopImageUrl.isBlank()) {
            this.desktopImageUrl = desktopImageUrl;
        }
        if (mobileImageUrl != null && !mobileImageUrl.isBlank()) {
            this.mobileImageUrl = mobileImageUrl;
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
    public Boolean getIsActive() { return isActive; }
    public Integer getDisplayOrder() { return displayOrder; }
    public String getDesktopImageUrl() { return desktopImageUrl; }
    public String getMobileImageUrl() { return mobileImageUrl; }
    public Boolean getActive() { return isActive; }
}