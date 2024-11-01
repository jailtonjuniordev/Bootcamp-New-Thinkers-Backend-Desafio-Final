package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.CreateUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.ResponseUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.UpdateUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.repository.UFRepository;
import com.jjdev.bootcamp_new_thinkers.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UFService {

    private final UFRepository ufRepository;

    public List<ResponseUFDTO> cadastrarUF(CreateUFDTO data) {
        this.verificarCamposDuplicados(data.nome(), data.sigla());

        UF ufCriada = new UF();
        ufCriada.setSigla(data.sigla().toUpperCase());
        ufCriada.setNome(data.nome().toUpperCase());
        ufCriada.setStatus(data.status());

        ufRepository.save(ufCriada);

        return ufRepository.findAll().stream()
                .map(uf -> new ResponseUFDTO(
                        uf.getCodigoUF(),
                        uf.getSigla(),
                        uf.getNome(),
                        uf.getStatus(),
                        uf.getAtualizadoEm(),
                        uf.getCriadoEm()
                ))
                .collect(Collectors.toList());
    }

    public List<ResponseUFDTO> editarUF(UpdateUFDTO data) {
        this.verificarCamposDuplicados(data.nome(), data.sigla());

        UF ufRecuperada = ufRepository.findById(data.codigoUF()).orElseThrow(
                () -> new CustomException("Código UF não encontrado!", HttpStatus.NOT_FOUND, null)
        );

        ufRecuperada.setSigla(data.sigla() != null ? data.sigla() : ufRecuperada.getSigla());
        ufRecuperada.setNome(data.nome() != null ? data.nome() : ufRecuperada.getNome());
        ufRecuperada.setStatus(data.status() != null ? data.status() : ufRecuperada.getStatus());

        ufRepository.save(ufRecuperada);

        return ufRepository.findAll().stream()
                .map(uf -> new ResponseUFDTO(
                        uf.getCodigoUF(),
                        uf.getSigla(),
                        uf.getNome(),
                        uf.getStatus(),
                        uf.getAtualizadoEm(),
                        uf.getCriadoEm()
                ))
                .collect(Collectors.toList());
    }

    public Object listarUF(Map<String, Object> parametros) {

        this.validarValoresParametros(parametros);

        List<UF> resultados = ufRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return new ResponseUFDTO(
                    resultados.get(0).getCodigoUF(),
                    resultados.get(0).getSigla(),
                    resultados.get(0).getNome(),
                    resultados.get(0).getStatus(),
                    resultados.get(0).getAtualizadoEm(),
                    resultados.get(0).getCriadoEm());
        } else {
            return resultados.stream()
                    .map(uf -> new ResponseUFDTO(
                            uf.getCodigoUF(),
                            uf.getSigla(),
                            uf.getNome(),
                            uf.getStatus(),
                            uf.getAtualizadoEm(),
                            uf.getCriadoEm()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public UF recuperarUFPorId(Long codigoUF) {
        return ufRepository.findById(codigoUF).orElseThrow(() -> new CustomException("UF não encontrada!", HttpStatus.NOT_FOUND, null));
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

    private void validarValoresParametros(Map<String, Object> parametros) {
        List<String> camposErrados = new ArrayList<>();

        if (parametros.get("codigoUF") != null) {
            String codigoUF = (String) parametros.get("codigoUF");
            if (!codigoUF.matches("\\d+")) {
                camposErrados.add("codigoUF");
            }
        }

        if (parametros.get("sigla") != null && !(parametros.get("sigla") instanceof String)) {
            camposErrados.add("sigla");
        }

        if (parametros.get("nome") != null && !(parametros.get("nome") instanceof String)) {
            camposErrados.add("nome");
        }

        if (parametros.get("status") != null) {
            String status = (String) parametros.get("status");
            if (!status.matches("\\d+") || Integer.parseInt(status) > 2) {
                camposErrados.add("status");
            }
        }

        if (!camposErrados.isEmpty()) {
            throw new CustomException("Existem campos inválidos detectados!", HttpStatus.BAD_REQUEST, camposErrados);
        }
    }
}
