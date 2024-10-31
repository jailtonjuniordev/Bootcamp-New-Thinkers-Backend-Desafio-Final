package com.jjdev.bootcamp_new_thinkers.domain.entity.endereco;

import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.Bairro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "enderecos")
@Schema(description = "Entidade de Enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codigo_municipio", nullable = false, unique = true)
    private Long codigoEndereco;

    @ManyToOne
    @JoinColumn(nullable = false, name = "codigo_bairro")
    private Bairro codigoBairro;

    @Column(name = "nome_rua", nullable = false)
    private String nomeRua;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String complemento;

    @Column(nullable = false)
    private String cep;

    @CreationTimestamp
    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;
}
