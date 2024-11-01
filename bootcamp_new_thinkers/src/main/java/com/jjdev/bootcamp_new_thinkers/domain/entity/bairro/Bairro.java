package com.jjdev.bootcamp_new_thinkers.domain.entity.bairro;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bairros")
@Schema(description = "Entidade de Bairros")
public class Bairro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_bairro", nullable = false, unique = true)
    private Long codigoBairro;

    @JoinColumn(name = "codigo_municipio", referencedColumnName = "codigo_municipio", nullable = false)
    @ManyToOne
    @JsonIgnoreProperties(value = "bairros")
    private Municipio codigoMunicipio;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do bairro deve conter apenas letras e espaços!")
    private String nome;

    @Column(nullable = false)
    @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
    @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
    private Integer status;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime AtualizadoEm;
}