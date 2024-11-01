package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.CreateMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.ResponseMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.UpdateMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import com.jjdev.bootcamp_new_thinkers.domain.repository.MunicipioRepository;
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
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final UFService ufService;

    public List<ResponseMunicipioDTO> cadastrarMunicipio(CreateMunicipioDTO data) {
        municipioRepository.findByNome(data.nome().toUpperCase()).ifPresent(
                municipio -> {
                    throw new CustomException("Já existe um municipio cadastrado com esse nome! tente novamente", HttpStatus.CONFLICT, null);
                });

        UF ufRecuperada = ufService.recuperarUFPorId(data.codigoUF());

        Municipio municipioCriado = new Municipio();

        municipioCriado.setNome(data.nome().toUpperCase());
        municipioCriado.setCodigoUF(ufRecuperada);
        municipioCriado.setStatus(data.status());

        municipioRepository.save(municipioCriado);

        return municipioRepository.findAll().stream()
                .map(municipio -> new ResponseMunicipioDTO(
                        municipio.getCodigoMunicipio(),
                        municipio.getCodigoUF().getCodigoUF(),
                        municipio.getNome(),
                        municipio.getStatus(),
                        municipio.getCriadoEm(),
                        municipio.getAtualizadoEm()
                ))
                .collect(Collectors.toList());

    }

    public Object listarMunicipio(Map<String, Object> parametros) {

        this.validarValoresParametros(parametros);

        List<Municipio> resultados = municipioRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return new ResponseMunicipioDTO(
                    resultados.get(0).getCodigoMunicipio(),
                    resultados.get(0).getCodigoUF().getCodigoUF(),
                    resultados.get(0).getNome(),
                    resultados.get(0).getStatus(),
                    resultados.get(0).getCriadoEm(),
                    resultados.get(0).getAtualizadoEm());
        } else {
            return resultados.stream()
                    .map(municipio -> new ResponseMunicipioDTO(
                            municipio.getCodigoMunicipio(),
                            municipio.getCodigoUF().getCodigoUF(),
                            municipio.getNome(),
                            municipio.getStatus(),
                            municipio.getCriadoEm(),
                            municipio.getAtualizadoEm()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public List<ResponseMunicipioDTO> editarMunicipio(UpdateMunicipioDTO data) {
        if (data.codigoMunicipio() == null) {
            List<String> fields = new ArrayList<>();
            fields.add("codigoMunicipio");
            throw new CustomException("O código do municipio deve ser informado!", HttpStatus.BAD_REQUEST, fields);
        }

        Municipio municipioRecuperado = municipioRepository.findById(data.codigoMunicipio()).orElseThrow(
                () -> new CustomException("Municipio não encontrado!", HttpStatus.NOT_FOUND, null)
        );

        if (data.codigoUF() != null) {
            UF ufRecuperada = ufService.recuperarUFPorId(data.codigoUF());
            municipioRecuperado.setCodigoUF(ufRecuperada);
        }

        if (data.nome() != null) {
            municipioRepository.findByNome(data.nome().toUpperCase()).ifPresent(
                    municipio -> {
                        throw new CustomException("Já existe um municipio cadastrado com esse nome! tente novamente", HttpStatus.CONFLICT, null);
                    });

            municipioRecuperado.setNome(data.nome().toUpperCase());
        }

        if (data.status() != null) {
            municipioRecuperado.setStatus(data.status());
        }

        municipioRepository.save(municipioRecuperado);

        return municipioRepository.findAll().stream()
                .map(municipio -> new ResponseMunicipioDTO(
                        municipio.getCodigoMunicipio(),
                        municipio.getCodigoUF().getCodigoUF(),
                        municipio.getNome(),
                        municipio.getStatus(),
                        municipio.getCriadoEm(),
                        municipio.getAtualizadoEm()
                ))
                .collect(Collectors.toList());
    }

    public Municipio recuperarMunicipioPorId(Long codigoMunicipio) {
        return municipioRepository.findById(codigoMunicipio).orElseThrow(() -> new CustomException("Municipio não encontrado!", HttpStatus.NOT_FOUND, null));
    }

    private void validarValoresParametros(Map<String, Object> parametros) {
        List<String> camposErrados = new ArrayList<>();

        if (parametros.get("codigoMunicipio") != null) {
            String codigoMunicipio = (String) parametros.get("codigoMunicipio");
            if (!codigoMunicipio.matches("\\d+")) {
                camposErrados.add("codigoMunicipio");
            }
        }

        if (parametros.get("codigoUF") != null) {
            String codigoUF = (String) parametros.get("codigoUF");
            if (!codigoUF.matches("\\d+")) {
                camposErrados.add("codigoUF");
            }
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
