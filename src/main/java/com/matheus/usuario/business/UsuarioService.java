package com.matheus.usuario.business;

import com.matheus.usuario.business.converter.UsuarioConverter;
import com.matheus.usuario.business.dto.UsuarioDTO;
import com.matheus.usuario.infrastructure.entity.UsuarioEntity;
import com.matheus.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter converter;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        UsuarioEntity usuario = converter.paraUsuario(usuarioDTO);
        return converter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
