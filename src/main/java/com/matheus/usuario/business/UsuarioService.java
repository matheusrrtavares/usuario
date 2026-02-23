package com.matheus.usuario.business;

import com.matheus.usuario.business.converter.UsuarioConverter;
import com.matheus.usuario.business.dto.EnderecoDTO;
import com.matheus.usuario.business.dto.TelefoneDTO;
import com.matheus.usuario.business.dto.UsuarioDTO;
import com.matheus.usuario.infrastructure.entity.Endereco;
import com.matheus.usuario.infrastructure.entity.Telefone;
import com.matheus.usuario.infrastructure.entity.Usuario;
import com.matheus.usuario.infrastructure.exceptions.ConflictException;
import com.matheus.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.matheus.usuario.infrastructure.repository.EnderecoRepository;
import com.matheus.usuario.infrastructure.repository.TelefoneRepository;
import com.matheus.usuario.infrastructure.repository.UsuarioRepository;
import com.matheus.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final UsuarioConverter usuarioConverter;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            emailExiste(usuarioDTO.getEmail());
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            usuario.setSenha(encoder.encode(usuario.getSenha()));
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não localizado " + email)
            ));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        //Busca email do usuário pelo token (tirar obrigatoriedade de passar o email)
        String email = jwtUtil.extractUsername(token.substring(7));

        //criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ?
                encoder.encode(usuarioDTO.getSenha()) : null);

        //Busca dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não localizado"));

        //Mesclou os dados que recebemos na requisição DTo com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        //salva dados do usuário convertido e depois pega o retorno e converte para DTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long enderecoId, EnderecoDTO enderecoDTO) {

        Endereco enderecoEntity = enderecoRepository.findById(enderecoId).orElseThrow(
                () -> new ResourceNotFoundException("ID não localizado " + enderecoId));

        Endereco endereco = usuarioConverter.atualizaEndereco(enderecoDTO, enderecoEntity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone (Long telefoneId, TelefoneDTO telefoneDTO) {

        Telefone telefoneEntity = telefoneRepository.findById(telefoneId).orElseThrow(
                () -> new ResourceNotFoundException("ID não localizado " + telefoneId));

        Telefone telefone = usuarioConverter.atualizaTelefone(telefoneDTO, telefoneEntity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Endereço não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());

        return  usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO cadastraTelefone (String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

}
