package br.com.vitallyoficial.api.domain.service;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.model.Product;
import br.com.vitallyoficial.api.domain.repository.CategoryRepository;
import br.com.vitallyoficial.api.domain.repository.ProductRepository;
import br.com.vitallyoficial.api.presentation.exception.GlobalExceptionHandler;
import br.com.vitallyoficial.api.presentation.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(String name, String description, BigDecimal price, String imageUrl, Integer displayOrder, UUID categoryId) {

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        }

        Product newProduct = Product.create(name, description, imageUrl, price, displayOrder, category);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(UUID id, String name, String description, String imageUrl, Integer displayOrder, UUID categoryId) {

        Product product = getProductById(id);

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        }

        product.updateInfo(name, description, imageUrl, displayOrder, category);

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