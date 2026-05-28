package br.com.vitallyoficial.api.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

public record StandardErrorDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String path,
        List<String> messages
) {}