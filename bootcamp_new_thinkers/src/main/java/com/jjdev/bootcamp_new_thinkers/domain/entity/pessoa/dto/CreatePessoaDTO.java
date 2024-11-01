package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.CreateEnderecoDTO;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreatePessoaDTO(
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome da pessoa deve conter apenas letras e espaços!")
        @NotBlank(message = "O nome não pode ser nulo!")
        String nome,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O sobrenome da pessoa deve conter apenas letras e espaços!")
        @NotBlank(message = "O Sobrenome não pode ser nulo!")
        String sobrenome,

        @NotNull(message = "A idade não pode ser nula!")
        @Min(value = 1, message = "A idade deve ser maior ou igual a 1")
        Integer idade,

        @NotBlank(message = "O login não pode ser nulo!")
        String login,

        @NotBlank(message = "A senha não pode ser nulo!")
        String senha,

        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        @NotNull(message = "O status não pode ser nulo.")
        Integer status,
        List<CreateEnderecoDTO> enderecos
) {
}
