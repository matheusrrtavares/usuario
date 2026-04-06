package com.matheus.usuario.business.converter;

import com.matheus.usuario.business.dto.EnderecoDTO;
import com.matheus.usuario.business.dto.TelefoneDTO;
import com.matheus.usuario.business.dto.UsuarioDTO;
import com.matheus.usuario.infrastructure.entity.EnderecoEntity;
import com.matheus.usuario.infrastructure.entity.TelefoneEntity;
import com.matheus.usuario.infrastructure.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    //DTO -> ENTITY

    public UsuarioEntity paraUsuario(UsuarioDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoEntity> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEnderecoEntity).toList();
    }

    public EnderecoEntity paraEnderecoEntity(EnderecoDTO dto) {
        return EnderecoEntity.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public List<TelefoneEntity> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneEntity).toList();
    }

    public TelefoneEntity paraTelefoneEntity(TelefoneDTO dto) {
        return TelefoneEntity.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    //ENTITY -> DTO

    public UsuarioDTO paraUsuarioDTO(UsuarioEntity entity) {
        return UsuarioDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .enderecos(paraListaEnderecoDTO(entity.getEnderecos()))
                .telefones(paraListaTelefoneDTO(entity.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<EnderecoEntity> enderecoEntities){
        return enderecoEntities.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(EnderecoEntity enderecoEntity) {
        return EnderecoDTO.builder()
                .rua(enderecoEntity.getRua())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .cep(enderecoEntity.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<TelefoneEntity> telefoneEntities) {
        return telefoneEntities.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(TelefoneEntity entity) {
        return TelefoneDTO.builder()
                .numero(entity.getNumero())
                .ddd(entity.getDdd())
                .build();
    }



}
