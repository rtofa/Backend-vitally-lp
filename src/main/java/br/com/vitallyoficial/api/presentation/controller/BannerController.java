package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.domain.model.Banner;
import br.com.vitallyoficial.api.domain.service.BannerService;
import br.com.vitallyoficial.api.presentation.dto.BannerRequestDTO;
import br.com.vitallyoficial.api.presentation.dto.BannerResponseDTO;
import br.com.vitallyoficial.api.presentation.dto.BannerUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/active")
    public ResponseEntity<List<BannerResponseDTO>> getActive() {

        List<Banner> bannersNoDominio = bannerService.getActiveBanners();

        List<BannerResponseDTO> response = bannersNoDominio.stream()
                .map(banner -> BannerResponseDTO.fromDomain(banner))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BannerResponseDTO>> getAll() {
        List<BannerResponseDTO> response = bannerService.getAllBanners().stream()
                .map(BannerResponseDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> findById(@PathVariable UUID id) {

        Banner banner = bannerService.findById(id);

        BannerResponseDTO response = BannerResponseDTO.fromDomain(banner);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BannerResponseDTO> create(@RequestBody @Valid BannerRequestDTO request) {
        Banner banner = bannerService.createBanner(
                request.title(),
                request.desktopImageUrl(),
                request.mobileImageUrl(),
                request.displayOrder());

        BannerResponseDTO response = BannerResponseDTO.fromDomain(banner);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> updatePartial(@PathVariable UUID id, @RequestBody @Valid BannerUpdateDTO request) {

        Banner updateBanner = bannerService.updateBanner(
                id,
                request.title(),
                request.desktopImageUrl(),
                request.mobileImageUrl(),
                request.displayOrder()
        );
        return ResponseEntity.ok(BannerResponseDTO.fromDomain(updateBanner));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID id, @RequestParam boolean isActive) {
        bannerService.changeStatus(id, isActive);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable UUID id){
        bannerService.deleteBannerById(id);

        return ResponseEntity.noContent().build();
    }


}