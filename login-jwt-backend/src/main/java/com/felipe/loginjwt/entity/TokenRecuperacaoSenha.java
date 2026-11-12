package com.felipe.loginjwt.entity;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class TokenRecuperacaoSenha {

   @Id
   private Long id;

   private Usuario usuario;

   private String tokenHash;

   private boolean usado;

   private LocalDateTime dataCriacao;

}
