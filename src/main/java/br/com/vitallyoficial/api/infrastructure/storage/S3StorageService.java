package br.com.vitallyoficial.api.infrastructure.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService {

    private final S3Client s3Client;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    @Value("${AWS_REGION}")
    private String region;

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadImage(MultipartFile file) {
        try {
            // Gera um nome único e remove espaços para evitar bugs em URLs
            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "imagem";
            String fileName = UUID.randomUUID() + "-" + originalName.replace(" ", "_");

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            // Executa o upload para a AWS
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Constrói e devolve a URL pública oficial do S3
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar o arquivo de imagem para o S3.", e);
        }
    }
}