package com.jjdev.bootcamp_new_thinkers.domain.entity.uf;

import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
import jakarta.persistence.*;
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
@Table(name = "ufs")
public class UF {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_uf", nullable = false, unique = true)
    private Long codigoUF;

    @Column(unique = true, nullable = false, length = 2)
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "A sigla do estado deve conter apenas duas letras!")
    private String sigla;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do estado deve conter apenas letras e espaços!")
    private String nome;

    @Column(nullable = false)
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Municipio> municipios;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;
}