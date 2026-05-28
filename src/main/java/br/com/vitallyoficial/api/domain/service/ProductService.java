package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.model.Product;
import br.com.vitallyoficial.api.domain.repository.ProductRepository;
import br.com.vitallyoficial.api.presentation.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, String description, BigDecimal price, String imageUrl, Integer displayOrder) {

        Product newProduct = Product.create(name, description, imageUrl, price, displayOrder);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(UUID id, String name, String description, String imageUrl, Integer displayOrder) {

        Product product = getProductById(id);

        product.updateInfo(name, description, imageUrl, displayOrder);

        return productRepository.save(product);
    }

    public void activateProduct(UUID id) {
        Product product = getProductById(id);
        product.activate();
        productRepository.save(product);
    }

    public void deactivateProduct(UUID id) {
        Product product = getProductById(id);
        product.deactive();
        productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        getProductById(id);
        productRepository.delete(id);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID fornecido: " + id));
    }

    public PageResult<Product> getProducts(String search, int page, int size) {
        return productRepository.findAllPaginated(search, page, size);
    }
}