package com.aprendizados.sbootsecurity.config;

import com.aprendizados.sbootsecurity.entity.Usuario;
import com.aprendizados.sbootsecurity.security.CustomAuthentication;
import com.aprendizados.sbootsecurity.security.IdentificacaoUsuario;
import com.aprendizados.sbootsecurity.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senha = (String)  authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioComPermissoes(login);

        if(usuario != null){
            boolean senhasIguais = passwordEncoder.matches(senha, usuario.getSenha());

            if (senhasIguais){
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getPermissoes()
                );
                return new CustomAuthentication(identificacaoUsuario);
            }
        }


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // vendo se a classe que chega é do tipo UsernamePasswordAuthenticationToken (É o que deve vir no nosso tipo de auth)
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
