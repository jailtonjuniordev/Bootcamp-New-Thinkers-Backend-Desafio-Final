package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.Endereco;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.UpdateEnderecoDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdatePessoaDTO(

        @NotNull(message = "O código da Pessoa não pode ser nulo!")
        Long codigoPessoa,
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome da pessoa deve conter apenas letras e espaços!")
        String nome,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O sobrenome da pessoa deve conter apenas letras e espaços!")
        String sobrenome,

        @Min(value = 1, message = "A idade deve ser maior ou igual a 1")
        Integer idade,

        String login,

        String senha,

        @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
        @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
        Integer status,
        List<UpdateEnderecoDTO> enderecos
) {
}
