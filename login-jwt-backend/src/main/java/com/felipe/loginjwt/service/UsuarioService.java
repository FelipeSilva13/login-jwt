package com.felipe.loginjwt.service;

import com.felipe.loginjwt.dto.request.CadastroRequest;
import com.felipe.loginjwt.dto.response.UsuarioResponse;
import com.felipe.loginjwt.entity.Usuario;
import com.felipe.loginjwt.mapper.UsuarioMapper;
import com.felipe.loginjwt.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(CadastroRequest cadastroRequest) {

        if (usuarioRepository.existsByEmail(cadastroRequest.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = usuarioMapper.toEntity(cadastroRequest);

        usuario.setSenha(passwordEncoder.encode(cadastroRequest.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioSalvo);

    }

}
