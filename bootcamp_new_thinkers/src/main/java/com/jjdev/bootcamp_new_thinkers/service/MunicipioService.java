package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.CreateMunicipioDTO;
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

@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final UFService ufService;

    public List<Municipio> cadastrarMunicipio(CreateMunicipioDTO data) {
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

        return municipioRepository.findAll();

    }

    public Object listarMunicipio(Map<String, Object> parametros) {

        List<Municipio> resultados = municipioRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return resultados.get(0);
        } else {
            return resultados;
        }
    }

    public List<Municipio> editarMunicipio(UpdateMunicipioDTO data) {
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

        return municipioRepository.findAll();
    }

    public Municipio recuperarMunicipio(Long codigoMunicipio) {
        return municipioRepository.findById(codigoMunicipio).orElseThrow(() -> new CustomException("Municipio não encontrado!", HttpStatus.NOT_FOUND, null));
    }
}
