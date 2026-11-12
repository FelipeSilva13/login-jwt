package com.felipe.loginjwt.security;

import com.felipe.loginjwt.entity.Usuario;
import com.felipe.loginjwt.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioReposiotry;

    public UserDetailsServiceImpl(UsuarioRepository usuarioReposiotry) {
        this.usuarioReposiotry = usuarioReposiotry;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioReposiotry.findByEmail(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Usuario não encontrado")
                );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(usuario.getRole())
                .disabled(!usuario.isAtivo())
                .build();

    }

}
