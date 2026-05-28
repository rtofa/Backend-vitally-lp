package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.infrastructure.storage.S3StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/uploads")
public class UploadController {

    private final S3StorageService storageService;

    public UploadController(S3StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileUrl = storageService.uploadImage(file);

        // Devolvemos um JSON simples com o link da imagem { "url": "https://..." }
        return ResponseEntity.ok(Map.of("url", fileUrl));
    }
}