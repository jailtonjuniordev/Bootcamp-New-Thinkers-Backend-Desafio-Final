package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.CreateUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.UpdateUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.repository.uf.UFRepository;
import com.jjdev.bootcamp_new_thinkers.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UFService {

    private final UFRepository ufRepository;

    public List<UF> cadastrarUF(CreateUFDTO data) {

        this.verificarCamposDuplicados(data.nome(), data.sigla());

        UF ufCriada = new UF();

        ufCriada.setSigla(data.sigla().toUpperCase());
        ufCriada.setNome(data.nome().toUpperCase());
        ufCriada.setStatus(data.status());

        ufRepository.save(ufCriada);

        return ufRepository.findAll();
    }

    public List<UF> editarUF(UpdateUFDTO data) {
        this.verificarCamposDuplicados(data.nome(), data.sigla());

        UF ufRecuperada = ufRepository.findById(data.codigoUF()).orElseThrow(
                () -> new CustomException("Código UF não encontrado!", HttpStatus.NOT_FOUND, null)
        );

        ufRecuperada.setSigla(data.sigla() != null ? data.sigla() : ufRecuperada.getSigla());
        ufRecuperada.setNome(data.nome() != null ? data.nome() : ufRecuperada.getNome());
        ufRecuperada.setStatus(data.status() != null ? data.status() : ufRecuperada.getStatus());

        ufRepository.save(ufRecuperada);

        return ufRepository.findAll();
    }

    public Object listarUF(Map<String, Object> parametros) {

        List<UF> resultados = ufRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return resultados.get(0);
        } else {
            return resultados;
        }
    }

    private void verificarCamposDuplicados(String nome, String sigla) {
        List<String> camposDuplicados = new ArrayList<>();

        if (sigla != null) {
            ufRepository.findBySiglaOrNome(sigla.toUpperCase(), null)
                    .ifPresent(uf -> {
                        camposDuplicados.add("sigla");
                    });
        }
        if (nome != null) {
            ufRepository.findBySiglaOrNome(null, nome.toUpperCase())
                    .ifPresent(uf -> {
                        camposDuplicados.add("nome");
                    });
        }

        if (!camposDuplicados.isEmpty()) {
            throw new CustomException(
                    "Não foi possível incluir UF no banco de dados. Existem campos duplicados!",
                    HttpStatus.CONFLICT,
                    camposDuplicados);
        }
    }
}
