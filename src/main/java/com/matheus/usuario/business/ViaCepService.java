package com.matheus.usuario.business;

import com.matheus.usuario.infrastructure.clients.ViaCepClient;
import com.matheus.usuario.infrastructure.clients.ViaCepDTO;
import com.matheus.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    public final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco(String cep){

        return viaCepClient.buscaDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep){

        if(cep == null){
            throw new IllegalArgumentException("O CEP não pode ser nulo");
        }

        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("\\d{8}")){
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos");
        };

        return cepFormatado;

    }

}
