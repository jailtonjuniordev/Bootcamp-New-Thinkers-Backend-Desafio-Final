package com.jjdev.bootcamp_new_thinkers.domain.entity.municipio;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.Bairro;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
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
@Table(name = "municipios")
@Schema(description = "Entidade de Municipios")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_municipio", nullable = false, unique = true)
    private Long codigoMunicipio;

    @JoinColumn(name = "codigo_uf", referencedColumnName = "codigo_uf", nullable = false)
    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigoUF")
    @JsonIdentityReference(alwaysAsId = true)
    private UF codigoUF;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome do municipio deve conter apenas letras e espaços!")
    private String nome;

    @Column(nullable = false)
    @Max(value = 2, message = "O status deve ser apenas 1 ou 2.")
    @Min(value = 1, message = "O status deve ser apenas 1 ou 2.")
    private Integer status;

    @OneToMany(mappedBy = "codigoMunicipio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bairro> bairros;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;
}
