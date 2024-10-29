package com.jjdev.bootcamp_new_thinkers.domain.entity.municipio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "municipios")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_municipio", nullable = false, unique = true)
    private Long codigoMunicipio;

    @JoinColumn(name = "codigo_uf", referencedColumnName = "codigoUF")
    @ManyToOne
    @JsonManagedReference
    private UF codigoUF;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do municipio deve conter apenas letras e espaços!")
    private String nome;

    @Column(nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;
}
