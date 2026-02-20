package com.matheus.usuario.infrastructure.repository;

import com.matheus.usuario.infrastructure.entity.Telefone;
import com.matheus.usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
