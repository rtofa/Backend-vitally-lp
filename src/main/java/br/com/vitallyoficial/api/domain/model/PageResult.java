package br.com.vitallyoficial.api.domain.model;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        long totalElements,
        int size
) {}