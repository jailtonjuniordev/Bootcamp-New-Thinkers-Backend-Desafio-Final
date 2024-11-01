package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto;

public record UFDTO(
        Long codigoUF,
        String sigla,
        String nome,
        Integer status
) {
}
