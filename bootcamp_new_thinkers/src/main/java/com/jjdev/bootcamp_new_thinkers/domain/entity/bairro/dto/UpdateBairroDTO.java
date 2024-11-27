package com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO de atualização de bairros")
public record UpdateBairroDTO(

        @NotNull(message = "O código do municipio não deve ser nulo!")
        Long codigoBairro,

        Long codigoMunicipio,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do bairro deve conter apenas letras e espaços!")
        String nome,

        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status
) {
}
