package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.domain.model.Category;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.service.CategoryService;
import br.com.vitallyoficial.api.presentation.dto.CategoryRequestDTO;
import br.com.vitallyoficial.api.presentation.dto.CategoryResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<PageResult<CategoryResponseDTO>> getAll(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PageResult<Category> categoryPage = categoryService.getCategories(search, page, size);

        List<CategoryResponseDTO> dtoContent = categoryPage.content().stream()
                .map(CategoryResponseDTO::fromDomain)
                .collect(Collectors.toList());

        PageResult<CategoryResponseDTO> responsePage = new PageResult<>(
                dtoContent,
                categoryPage.currentPage(),
                categoryPage.totalPages(),
                categoryPage.totalElements(),
                categoryPage.size()
        );

        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryResponseDTO.fromDomain(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO request) {
        Category createdCategory = categoryService.createCategory(
                request.name(),
                request.imageUrl()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryResponseDTO.fromDomain(createdCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequestDTO request
    ) {
        Category updatedCategory = categoryService.updateCategory(
                id,
                request.name(),
                request.imageUrl()
        );

        return ResponseEntity.ok(CategoryResponseDTO.fromDomain(updatedCategory));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        categoryService.activateCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}