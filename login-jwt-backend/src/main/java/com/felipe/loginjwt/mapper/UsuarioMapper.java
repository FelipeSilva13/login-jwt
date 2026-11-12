package com.felipe.loginjwt.mapper;

import com.felipe.loginjwt.dto.request.CadastroRequest;
import com.felipe.loginjwt.dto.response.UsuarioResponse;
import com.felipe.loginjwt.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(CadastroRequest cadastroRequest) {
        Usuario usuario = new Usuario();

        usuario.setNome(cadastroRequest.getNome());
        usuario.setEmail(cadastroRequest.getEmail());

        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setRole(usuario.getRole());

        return usuarioResponse;
    }
}
