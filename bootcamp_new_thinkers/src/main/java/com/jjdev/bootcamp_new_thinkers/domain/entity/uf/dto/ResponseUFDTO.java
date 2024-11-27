package com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto;

import java.time.LocalDateTime;

public record ResponseUFDTO(
        Long codigoUF,
        String sigla,
        String nome,
        Integer status,
        LocalDateTime atualizadoEm,
        LocalDateTime criadoEm
) {
}
