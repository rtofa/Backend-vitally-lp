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

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadImage(MultipartFile file) {
        try {
            // Gera um nome único para evitar sobreposição de arquivos
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename().replace(" ", "_");

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    // ACL pública para que a imagem possa ser vista no site (depende da config do seu bucket)
                    // .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Constrói e devolve o URL público do ficheiro gerado pela AWS
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar o arquivo de imagem.", e);
        }
    }
}