package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.model.Product;
import br.com.vitallyoficial.api.domain.service.ProductService;
import br.com.vitallyoficial.api.presentation.dto.ProductRequestDTO;
import br.com.vitallyoficial.api.presentation.dto.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<PageResult<ProductResponseDTO>> getAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PageResult<Product> productPage = productService.getProducts(search, page, size);

        List<ProductResponseDTO> dtoContent = productPage.content().stream()
                .map(ProductResponseDTO::fromDomain)
                .collect(Collectors.toList());

        PageResult<ProductResponseDTO> responsePage = new PageResult<>(
                dtoContent,
                productPage.currentPage(),
                productPage.totalPages(),
                productPage.totalElements(),
                productPage.size()
        );

        return ResponseEntity.ok(responsePage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductResponseDTO.fromDomain(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO request) {
        Product createdProduct = productService.createProduct(
                request.name(),
                request.description(),
                request.price(),
                request.imageUrl(),
                request.displayOrder()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseDTO.fromDomain(createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request) {
        Product updatedProduct = productService.updateProduct(
                id,
                request.name(),
                request.description(),
                request.imageUrl(),
                request.displayOrder()
        );
        return ResponseEntity.ok(ProductResponseDTO.fromDomain(updatedProduct));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        productService.activateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}