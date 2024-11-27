package com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto;

import java.time.LocalDateTime;

public record ResponseBairroDTO(
        Long codigoBairro,
        Long codigoMunicipio,
        String nome,
        Integer status,
        LocalDateTime criadoEm,
        LocalDateTime AtualizadoEm
) {
}
