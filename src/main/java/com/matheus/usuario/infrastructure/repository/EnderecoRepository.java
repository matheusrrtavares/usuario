package com.matheus.usuario.infrastructure.repository;

import com.matheus.usuario.infrastructure.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

}
