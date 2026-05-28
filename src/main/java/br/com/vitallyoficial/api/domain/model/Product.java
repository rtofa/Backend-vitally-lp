package br.com.vitallyoficial.api.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String productName;
    private String productDescription;
    private String imageUrl;
    private BigDecimal price;
    private Integer displayOrder;
    private Boolean isActive;

    public Product(UUID id, String productName, String productDescription, String imageUrl, BigDecimal price, Integer displayOrder, Boolean isActive) {
        if (productName == null || productName.isBlank()) throw new IllegalArgumentException("O nome do produto é obrigatório.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("O preço deve ser maior que zero.");

        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imageUrl = imageUrl;
        this.price = price;
        this.displayOrder = displayOrder;
        this.isActive = isActive != null ? isActive : true;
    }

    public static Product create(String productName, String productDescription, String imageUrl, BigDecimal price, Integer displayOrder) {
        return new Product(
                UUID.randomUUID(),
                productName,
                productDescription,
                imageUrl,
                price,
                displayOrder,
                true
        );
    }

    public static Product restore(UUID id, String productName, String productDescription, String imageUrl, BigDecimal price, Integer displayOrder, Boolean isActive) {
        return new Product(
                id,
                productName,
                productDescription,
                imageUrl,
                price,
                displayOrder,
                isActive
        );
    }


    public void updateInfo(String productName, String productDescription, String imageUrl, Integer displayOrder){

        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrl = imageUrl;
        }
        if (displayOrder != null) {
            this.displayOrder = displayOrder;
        }
        if (productDescription != null && !productDescription.isBlank()) {
            this.productDescription = productDescription;
        }
    }

    public void activate(){ this.isActive = true; }

    public void deactive(){ this.isActive = false; }



    public UUID getId() { return id; }

    public String getProductName() { return productName; }

    public String getProductDescription() { return productDescription; }

    public String getImageUrl() { return imageUrl; }

    public BigDecimal getPrice() { return price; }

    public Integer getDisplayOrder() { return displayOrder; }

    public Boolean getActive() { return isActive; }

}
