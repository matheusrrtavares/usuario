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

    public List<EnderecoDTO> paraListaEnderecoDTO(List<EnderecoEntity> enderecoEntities) {
        return enderecoEntities.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(EnderecoEntity enderecoEntity) {
        return EnderecoDTO.builder()
                .id(enderecoEntity.getId())
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
                .id(entity.getId())
                .numero(entity.getNumero())
                .ddd(entity.getDdd())
                .build();
    }

    //PUT
    public UsuarioEntity updateUsuario(UsuarioEntity entity, UsuarioDTO dto) {
        return UsuarioEntity.builder()
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .id(entity.getId())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();

    }

    //Update telefone e endereco

    public EnderecoEntity updateEndereco(EnderecoEntity entity, EnderecoDTO dto){
        return EnderecoEntity.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public TelefoneEntity updateTelefone(TelefoneEntity entity, TelefoneDTO dto) {
        return TelefoneEntity.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }
}
