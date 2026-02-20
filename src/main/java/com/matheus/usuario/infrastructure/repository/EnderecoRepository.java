package com.matheus.usuario.infrastructure.repository;

import com.matheus.usuario.infrastructure.entity.Endereco;
import com.matheus.usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
