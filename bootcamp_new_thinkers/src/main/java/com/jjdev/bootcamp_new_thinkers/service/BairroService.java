package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.Bairro;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.CreateBairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.UpdateBairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
import com.jjdev.bootcamp_new_thinkers.domain.repository.BairroRepository;
import com.jjdev.bootcamp_new_thinkers.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;
    private final MunicipioService municipioService;

    public List<Bairro> cadastrarBairro(CreateBairroDTO data) {
        bairroRepository.findByNome(data.nome().toUpperCase()).ifPresent(
                bairro -> {
                    throw new CustomException("Já existe um bairro cadastrado com esse nome! tente novamente", HttpStatus.CONFLICT, null);
                });

        Municipio municipioRecebido = municipioService.recuperarMunicipioPorId(data.codigoMunicipio());

        Bairro bairroCriado = new Bairro();
        bairroCriado.setNome(data.nome().toUpperCase());
        bairroCriado.setCodigoMunicipio(municipioRecebido);
        bairroCriado.setStatus(data.status());

        bairroRepository.save(bairroCriado);

        return bairroRepository.findAll();
    }

    public Object listarBairros(Map<String, Object> parametros) {

        List<Bairro> resultados = bairroRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return resultados.get(0);
        } else {
            return resultados;
        }
    }

    public List<Bairro> editarBairro(UpdateBairroDTO data) {

        if (data.codigoBairro() == null) {
            List<String> fields = new ArrayList<>();
            fields.add("codigoBairro");
            throw new CustomException("O código do bairro deve ser informado!", HttpStatus.BAD_REQUEST, fields);
        }

        Bairro bairroRecuperado = bairroRepository.findById(data.codigoBairro()).orElseThrow(
                () -> new CustomException("Bairro não encontrado!", HttpStatus.NOT_FOUND, null)
        );

        if (data.codigoMunicipio() != null) {
            Municipio municipioRecuperado = municipioService.recuperarMunicipioPorId(data.codigoMunicipio());
            bairroRecuperado.setCodigoMunicipio(municipioRecuperado);
        }

        if (data.nome() != null) {
            bairroRepository.findByNome(data.nome().toUpperCase()).ifPresent(
                    bairro -> {
                        throw new CustomException("Já existe um bairro cadastrado com esse nome! tente novamente", HttpStatus.CONFLICT, null);
                    });

            bairroRecuperado.setNome(data.nome().toUpperCase());
        }

        if (data.status() != null) {
            bairroRecuperado.setStatus(data.status());
        }

        bairroRepository.save(bairroRecuperado);

        return bairroRepository.findAll();
    }

    public Bairro recuperarBairroPorId(Long codigoBairro) {
        return bairroRepository.findById(codigoBairro).orElseThrow(() -> new CustomException("Bairro não encontrado!", HttpStatus.NOT_FOUND, null));
    }

}
