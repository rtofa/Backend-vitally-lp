package br.com.vitallyoficial.api.presentation.controller;

import br.com.vitallyoficial.api.infrastructure.entity.AdminEntity;
import br.com.vitallyoficial.api.infrastructure.security.TokenService;
import br.com.vitallyoficial.api.presentation.dto.AuthDTO;
import br.com.vitallyoficial.api.presentation.dto.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AuthDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((AdminEntity) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}