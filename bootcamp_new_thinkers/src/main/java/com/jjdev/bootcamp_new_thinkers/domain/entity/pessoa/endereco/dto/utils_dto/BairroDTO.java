package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto;

public record BairroDTO(
        Long codigoBairro,
        Long codigoMunicipio,
        String nome,
        Integer status,
        MunicipioDTO municipio
) {
}
