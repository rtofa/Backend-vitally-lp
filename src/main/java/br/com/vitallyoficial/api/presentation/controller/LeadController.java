package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.LeadItem;
import br.com.vitallyoficial.api.domain.model.PageResult;
import br.com.vitallyoficial.api.domain.service.LeadService;
import br.com.vitallyoficial.api.presentation.dto.LeadRequestDTO;
import br.com.vitallyoficial.api.presentation.dto.LeadResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public ResponseEntity<PageResult<LeadResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResult<LeadResponseDTO> result = leadService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<LeadResponseDTO> create(@RequestBody @Valid LeadRequestDTO request) {


        List<LeadItem> domainItems = new ArrayList<>();
        if (request.items() != null) {

            domainItems = request.items().stream()
                    .map(itemDto
                    -> new LeadItem(itemDto.productId(), itemDto.quantity()))
                    .collect(Collectors.toList());
        }


        Lead createdLead = leadService.createLead(
                request.name(),
                request.phone(),
                request.email(),
                request.message(),
                request.city(),
                request.state(),
                request.type(),
                domainItems,
                request.segment()
        );


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LeadResponseDTO.fromDomain(createdLead));
    }


}