package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.Bairro;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.CreateBairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.ResponseBairroDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;
    private final MunicipioService municipioService;

    public List<ResponseBairroDTO> cadastrarBairro(CreateBairroDTO data) {
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

        return bairroRepository.findAll().stream()
                .map(bairro -> new ResponseBairroDTO(
                        bairro.getCodigoBairro(),
                        bairro.getCodigoMunicipio().getCodigoMunicipio(),
                        bairro.getNome(),
                        bairro.getStatus(),
                        bairro.getCriadoEm(),
                        bairro.getAtualizadoEm()
                ))
                .collect(Collectors.toList());
    }

    public Object listarBairros(Map<String, Object> parametros) {
        this.validarValoresParametros(parametros);

        List<Bairro> resultados = bairroRepository.buscarPorParametros(parametros);

        if (resultados.size() == 1) {
            return new ResponseBairroDTO(
                    resultados.get(0).getCodigoBairro(),
                    resultados.get(0).getCodigoMunicipio().getCodigoMunicipio(),
                    resultados.get(0).getNome(),
                    resultados.get(0).getStatus(),
                    resultados.get(0).getCriadoEm(),
                    resultados.get(0).getAtualizadoEm()
            );
        } else {
            return resultados.stream()
                    .map(bairro -> new ResponseBairroDTO(
                            bairro.getCodigoBairro(),
                            bairro.getCodigoMunicipio().getCodigoMunicipio(),
                            bairro.getNome(),
                            bairro.getStatus(),
                            bairro.getCriadoEm(),
                            bairro.getAtualizadoEm()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public List<ResponseBairroDTO> editarBairro(UpdateBairroDTO data) {

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

        return bairroRepository.findAll().stream()
                .map(bairro -> new ResponseBairroDTO(
                        bairro.getCodigoBairro(),
                        bairro.getCodigoMunicipio().getCodigoMunicipio(),
                        bairro.getNome(),
                        bairro.getStatus(),
                        bairro.getCriadoEm(),
                        bairro.getAtualizadoEm()
                ))
                .collect(Collectors.toList());
    }

    public Bairro recuperarBairroPorId(Long codigoBairro) {
        return bairroRepository.findById(codigoBairro).orElseThrow(() -> new CustomException("Bairro não encontrado!", HttpStatus.NOT_FOUND, null));
    }

    private void validarValoresParametros(Map<String, Object> parametros) {
        List<String> camposErrados = new ArrayList<>();

        if (parametros.get("codigoMunicipio") != null) {
            String codigoMunicipio = (String) parametros.get("codigoMunicipio");
            if (!codigoMunicipio.matches("\\d+")) {
                camposErrados.add("codigoMunicipio");
            }
        }

        if (parametros.get("codigoBairro") != null) {
            String codigoBairro = (String) parametros.get("codigoBairro");
            if (!codigoBairro.matches("\\d+")) {
                camposErrados.add("codigoBairro");
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
