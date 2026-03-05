package com.aprendizados.sbootsecurity.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class CustomAuthentication implements Authentication {

    private final IdentificacaoUsuario identificacaoUsuario;

    public CustomAuthentication(IdentificacaoUsuario identificacaoUsuario) {
        if(identificacaoUsuario == null){
            throw new ExceptionInInitializerError("Não é possível criar um custom Authentication sem a identificação do usuário");
        }
        this.identificacaoUsuario = identificacaoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.identificacaoUsuario
                .getPermissoes()
                .stream()
                .map(perm -> new SimpleGrantedAuthority(perm)) // recebe o role que ele vai ter
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null; // o user já vai ta logado então vai retornar null pra não expor ele
    }

    @Override
    public Object getDetails() {
        return null; // pode colocar metadados (ultimo login, onde logou)
    }

    @Override
    public Object getPrincipal() {
        return this.identificacaoUsuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Não precisa chamar, já estamos autenticados!");
    }

    @Override
    public String getName() {
        return this.identificacaoUsuario.getNome();
    }
}
