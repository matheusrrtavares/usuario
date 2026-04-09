package com.matheus.usuario.business;

import com.matheus.usuario.business.converter.UsuarioConverter;
import com.matheus.usuario.business.dto.EnderecoDTO;
import com.matheus.usuario.business.dto.TelefoneDTO;
import com.matheus.usuario.business.dto.UsuarioDTO;
import com.matheus.usuario.infrastructure.entity.EnderecoEntity;
import com.matheus.usuario.infrastructure.entity.TelefoneEntity;
import com.matheus.usuario.infrastructure.entity.UsuarioEntity;
import com.matheus.usuario.infrastructure.exceptions.ConflictException;
import com.matheus.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.matheus.usuario.infrastructure.repository.EnderecoRepository;
import com.matheus.usuario.infrastructure.repository.TelefoneRepository;
import com.matheus.usuario.infrastructure.repository.UsuarioRepository;
import com.matheus.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        UsuarioEntity usuario = converter.paraUsuario(usuarioDTO);
        return converter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {

        boolean existe = verificaEmailExistente(email);
        if (existe) {
            throw new ConflictException("Email já cadastrado: " + email);
        }

    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioEntity buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        UsuarioEntity entity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));

        entity = converter.updateUsuario(entity, dto);

        return converter.paraUsuarioDTO(usuarioRepository.save(entity));
    }

    public EnderecoDTO atualizaEndereco(Long enderecoId, EnderecoDTO enderecoDTO) {

        EnderecoEntity entity = enderecoRepository.findById(enderecoId)
                .orElseThrow( () -> new ResourceNotFoundException("Id não encontrado " + enderecoId));

        EnderecoEntity endereco = converter.updateEndereco(entity, enderecoDTO);

        return converter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone(Long telefoneId, TelefoneDTO telefoneDTO) {

        TelefoneEntity entity = telefoneRepository.findById(telefoneId)
                .orElseThrow( () -> new ResourceNotFoundException("Id não encontrado " + telefoneId));

        TelefoneEntity telefone = converter.updateTelefone(entity, telefoneDTO);

        return converter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }




}
