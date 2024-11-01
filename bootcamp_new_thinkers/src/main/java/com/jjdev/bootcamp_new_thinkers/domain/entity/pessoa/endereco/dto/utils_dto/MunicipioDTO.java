package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto;

public record MunicipioDTO(
        Long codigoMunicipio,
        Long codigoUF,
        String nome,
        Integer status,
        UFDTO uf
) {
}
