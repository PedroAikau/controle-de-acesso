package com.aprendizados.sbootsecurity.entity;


import lombok.Data;

import java.util.List;

@Data
public class CadastroUsuarioDTO {

    private Usuario usuario;
    private List<String> permissoes; // nome de cada grupo que ele vai fazer parte
}
