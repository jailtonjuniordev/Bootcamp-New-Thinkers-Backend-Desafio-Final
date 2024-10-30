package com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO de criação de bairros")
public record CreateBairroDTO(
        @NotNull(message = "O código do Municipio não pode ser vazio!")
        Long codigoMunicipio,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do bairro deve conter apenas letras e espaços!")
        @NotBlank(message = "O nome do municipio não pode ser vazio!")
        String nome,

        @NotNull(message = "o status não pode ser nulo!")
        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status
) {
}
