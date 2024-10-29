package com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto;

import jakarta.validation.constraints.*;

public record CreateUFDTO(

        @NotBlank(message = "O nome da sigla do estado não pode ser vazio!")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "A sigla do estado deve conter apenas duas letras!")
        String sigla,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do estado deve conter apenas letras e espaços!")
        @NotBlank(message = "o nome do estado não pode ser vazio!")
        String nome,

        @NotNull(message = "o status não pode ser nulo!")
        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status
) {
}
