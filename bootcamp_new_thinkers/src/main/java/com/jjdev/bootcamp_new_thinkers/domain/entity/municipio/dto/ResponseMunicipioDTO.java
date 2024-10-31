package com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto;

import java.time.LocalDateTime;

public record ResponseMunicipioDTO(
        Long codigoMunicipio,
        Long condigoUF,
        String nome,
        Integer status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
