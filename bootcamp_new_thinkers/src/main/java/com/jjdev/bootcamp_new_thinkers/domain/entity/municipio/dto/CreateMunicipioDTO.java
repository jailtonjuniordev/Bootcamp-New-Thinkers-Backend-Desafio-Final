package com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import jakarta.validation.constraints.*;

public record CreateMunicipioDTO(
        @NotNull(message = "O código do estado não pode ser vazio!")
        Long codigoUF,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do municipio deve conter apenas letras e espaços!")
        @NotBlank(message = "O nome do municipio não pode ser vazio!")
        String nome,

        @NotNull(message = "o status não pode ser nulo!")
        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status
) {
}
