package com.matheus.usuario.infrastructure.repository;

import com.matheus.usuario.infrastructure.entity.TelefoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<TelefoneEntity, Long> {
}
