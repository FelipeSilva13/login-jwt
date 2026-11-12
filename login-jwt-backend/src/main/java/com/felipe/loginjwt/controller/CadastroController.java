package com.felipe.loginjwt.controller;

import com.felipe.loginjwt.dto.request.CadastroRequest;
import com.felipe.loginjwt.dto.response.UsuarioResponse;
import com.felipe.loginjwt.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CadastroController {

    private final UsuarioService usuarioService;


    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(
            @Valid @RequestBody CadastroRequest request){

        UsuarioResponse usuarioResponse = usuarioService.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }
}
