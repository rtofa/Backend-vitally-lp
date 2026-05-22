package br.com.vitallyoficial.api.domain;

public class LeadItem {

    private Long productId;
    private Integer quantity;

    public LeadItem(Long productId, Integer quantity){
        if (productId == null){
            throw new IllegalArgumentException("O ID do produto é obrigatório");
        }
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException("A quantidade deve ser de pelo menos 1");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() { return productId; }

    public Integer getQuantity() { return quantity; }
}
