package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Endereco}
 */
public record UpdateEnderecoDTO(

        Long codigoEndereco,

        @NotNull(message = "O código pessoa não pode ser nulo.")
        Long codigoPessoa,

        @NotNull(message = "O código do Bairro não pode ser nulo.")
        Long codigoBairro,

        @NotBlank(message = "Nome da rua não pode ser vazio")
        String nomeRua,

        @NotBlank(message = "O numero não pode ser vazio")
        String numero,

        @NotBlank(message = "O complemento não pode ser vazio")
        String complemento,

        @NotBlank(message = "O CEP não pode ser vazio")
        String cep) implements Serializable {
}