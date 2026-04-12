package com.matheus.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "telefone")
@Builder
public class TelefoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "ddd", length = 3)
    private String ddd;
    @Column(name = "usuario_id")
    private Long usuario_id;
}
