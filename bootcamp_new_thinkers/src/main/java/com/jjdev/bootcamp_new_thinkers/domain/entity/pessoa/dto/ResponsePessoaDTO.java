package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.Endereco;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.ResponseEnderecoDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePessoaDTO(
        Long codigoPessoa,
        String nome,
        String sobrenome,
        Integer idade,
        String login,
        String senha,
        Integer status,
        List<ResponseEnderecoDTO> enderecos,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
