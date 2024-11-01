package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto.BairroDTO;

public record ResponseEnderecoDTO(
        Long codigoEndereco,
        Long codigoPessoa,
        Long codigoBairro,
        String nomeRua,
        String complemento,
        String cep,
        BairroDTO bairro
) {
}
