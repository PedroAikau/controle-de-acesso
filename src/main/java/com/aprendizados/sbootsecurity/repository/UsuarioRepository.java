package com.aprendizados.sbootsecurity.repository;

import com.aprendizados.sbootsecurity.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);
}
