package com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoas")
@Schema(description = "Entidade de Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoPessoa;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome da pessoa deve conter apenas letras e espaços!")
    @Column(nullable = false)
    private String nome;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O sobrenome da pessoa deve conter apenas letras e espaços!")
    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    @Min(value = 1, message = "A idade precisa ser maior ou igual a 1.")
    private Integer idade;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
    @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
    private Integer status;

    @OneToMany(mappedBy = "codigoPessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "codigoPessoa")
    private List<Endereco> enderecos;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime AtualizadoEm;
}
