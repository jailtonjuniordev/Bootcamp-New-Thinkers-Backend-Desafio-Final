package com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.core.annotation.Order;

@Schema(description = "DTO de atualização de UF (Estados)")
public record UpdateUFDTO(

        @NotNull(message = "O Código da UF não pode ser nulo!")
        Long codigoUF,

        @Pattern(regexp = "^[A-Za-z]{2}$", message = "A sigla do estado deve conter apenas duas letras!")
        String sigla,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do estado deve conter apenas letras e espaços!")
        String nome,

        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status
) {
}
