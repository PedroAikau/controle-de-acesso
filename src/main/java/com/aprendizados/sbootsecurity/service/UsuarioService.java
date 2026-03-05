package com.aprendizados.sbootsecurity.service;

import com.aprendizados.sbootsecurity.entity.Grupo;
import com.aprendizados.sbootsecurity.entity.Usuario;
import com.aprendizados.sbootsecurity.entity.UsuarioGrupo;
import com.aprendizados.sbootsecurity.repository.GrupoRepository;
import com.aprendizados.sbootsecurity.repository.UsuarioGrupoRepository;
import com.aprendizados.sbootsecurity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final GrupoRepository grupoRepository;

    private final UsuarioGrupoRepository usuarioGrupoRepository;

    private final PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario, List<String> permissoes){

        // Salvando o usuário no bd
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        List<UsuarioGrupo> listaUsuarioGrupo = permissoes.stream().map(nomeGrupo -> {

            // Pegando os grupos existentes
            Optional< Grupo> possivelGrupo = grupoRepository.findByNome(nomeGrupo);

            // Caso não tenha, retorna null
            if(!possivelGrupo.isPresent()) return null;

            // Cadastrando o usuário e o grupo.
            Grupo grupo = possivelGrupo.get();
            return new UsuarioGrupo(usuario, grupo);
        }).filter(grupo -> grupo != null).collect(Collectors.toList()); // precisa ser um grupo não null e transforma em list
        usuarioGrupoRepository.saveAll(listaUsuarioGrupo);

        return usuario;
    }

    public Usuario obterUsuarioComPermissoes(String login){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        if(usuarioOptional.isEmpty()) return null;

        Usuario usuario = usuarioOptional.get();
        List<String> permissoes = usuarioGrupoRepository.findPermissoesByUsuario(usuario);
        usuario.setPermissoes(permissoes);

        return usuario;


    }
}
