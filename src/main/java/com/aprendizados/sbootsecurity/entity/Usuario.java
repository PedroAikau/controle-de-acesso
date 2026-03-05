package com.aprendizados.sbootsecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data // cria getter e setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String login;
    private String senha;
    private String nome;

    @Transient // não é uma coluna, então vamos fazer o jpa ignorar na hora do mapeamento
    private List<String> permissoes;
}
