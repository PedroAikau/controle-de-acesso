package com.aprendizados.sbootsecurity.repository;


import com.aprendizados.sbootsecurity.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {

    Optional<Grupo> findByNome(String nome);
}
