package com.felipe.loginjwt.service;


import com.felipe.loginjwt.dto.request.LoginRequest;
import com.felipe.loginjwt.dto.response.LoginResponse;
import com.felipe.loginjwt.entity.Usuario;
import com.felipe.loginjwt.mapper.UsuarioMapper;
import com.felipe.loginjwt.repository.UsuarioRepository;
import com.felipe.loginjwt.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.gerarToken(userDetails);

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationInSeconds(),
                usuarioMapper.toResponse(usuario)
        );

    }
}
