package com.jjdev.bootcamp_new_thinkers.domain.entity.uf;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UF {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_uf", nullable = false, unique = true)
    private Long codigoUF;

    @Column(unique = true, nullable = false, length = 2)
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "O nome do estado deve conter apenas duas letras!")
    private String sigla;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+$", message = "O nome do estado deve conter apenas letras!")
    private String nome;

    @Column(nullable = false)
    private int status;
}
