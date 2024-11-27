package com.jjdev.bootcamp_new_thinkers.service;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.Pessoa;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.CreatePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.ResponsePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.UpdatePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.Endereco;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.CreateEnderecoDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.ResponseEnderecoDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.UpdateEnderecoDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto.BairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto.MunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.endereco.dto.utils_dto.UFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.repository.PessoaRepository;
import com.jjdev.bootcamp_new_thinkers.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final BairroService bairroService;

    public List<ResponsePessoaDTO> cadastrarPessoa(CreatePessoaDTO data) {

        pessoaRepository.findByLogin(data.login()).ifPresent(
                pessoa -> {
                    throw new CustomException("Já existe uma pessoa com esse login! tente novamente", HttpStatus.CONFLICT, null);
                }
        );

        Pessoa pessoaCriada = new Pessoa();

        pessoaCriada.setNome(data.nome().toLowerCase());
        pessoaCriada.setSobrenome(data.sobrenome().toLowerCase());
        pessoaCriada.setIdade(data.idade());
        pessoaCriada.setLogin(data.login().toLowerCase());
        pessoaCriada.setSenha(data.senha());
        pessoaCriada.setStatus(data.status());
        pessoaCriada.setEnderecos(new ArrayList<>());

        for (CreateEnderecoDTO endereco : data.enderecos()) {
            Endereco novoEndereco = new Endereco();
            novoEndereco.setCodigoBairro(bairroService.recuperarBairroPorId(endereco.codigoBairro()));
            novoEndereco.setCodigoPessoa(pessoaCriada);
            novoEndereco.setNomeRua(endereco.nomeRua());
            novoEndereco.setNumero(endereco.numero());
            novoEndereco.setComplemento(endereco.complemento());
            novoEndereco.setCep(endereco.cep());

            pessoaCriada.getEnderecos().add(novoEndereco);
        }

        pessoaRepository.save(pessoaCriada);

        return pessoaRepository.findAll().stream().map(
                pessoa -> new ResponsePessoaDTO(
                        pessoa.getCodigoPessoa(),
                        pessoa.getNome(),
                        pessoa.getSobrenome(),
                        pessoa.getIdade(),
                        pessoa.getLogin(),
                        pessoa.getSenha(),
                        pessoa.getStatus(),
                        new ArrayList<>(),
                        pessoa.getCriadoEm(),
                        pessoa.getAtualizadoEm()
                )).collect(Collectors.toList());
    }

    public Object buscarPorParametros(Map<String, Object> parametros) {
        this.validarValoresParametros(parametros);

        List<Pessoa> resultados = pessoaRepository.buscarPorParametros(parametros);

        if (parametros.get("codigoPessoa") != null) {
            return resultados.stream().map(
                    pessoa -> new ResponsePessoaDTO(
                            pessoa.getCodigoPessoa(),
                            pessoa.getNome(),
                            pessoa.getSobrenome(),
                            pessoa.getIdade(),
                            pessoa.getLogin(),
                            pessoa.getSenha(),
                            pessoa.getStatus(),
                            pessoa.getEnderecos().stream().map(endereco -> new ResponseEnderecoDTO(
                                    endereco.getCodigoEndereco(),
                                    pessoa.getCodigoPessoa(),
                                    endereco.getCodigoBairro().getCodigoBairro(),
                                    endereco.getNomeRua(),
                                    endereco.getComplemento(),
                                    endereco.getCep(),
                                    new BairroDTO(
                                            endereco.getCodigoBairro().getCodigoBairro(),
                                            endereco.getCodigoBairro().getCodigoMunicipio().getCodigoMunicipio(),
                                            endereco.getCodigoBairro().getNome(),
                                            endereco.getCodigoBairro().getStatus(),
                                            new MunicipioDTO(
                                                    endereco.getCodigoBairro().getCodigoBairro(),
                                                    endereco.getCodigoBairro().getCodigoMunicipio().getCodigoUF().getCodigoUF(),
                                                    endereco.getCodigoBairro().getCodigoMunicipio().getNome(),
                                                    endereco.getCodigoBairro().getCodigoMunicipio().getStatus(),
                                                    new UFDTO(
                                                            endereco.getCodigoBairro().getCodigoMunicipio().getCodigoUF().getCodigoUF(),
                                                            endereco.getCodigoBairro().getCodigoMunicipio().getCodigoUF().getSigla(),
                                                            endereco.getCodigoBairro().getCodigoMunicipio().getCodigoUF().getNome(),
                                                            endereco.getCodigoBairro().getCodigoMunicipio().getCodigoUF().getStatus()
                                                    )
                                            ))
                            )).collect(Collectors.toList()),
                            pessoa.getCriadoEm(),
                            pessoa.getAtualizadoEm()
                    ));
        } else {
            return resultados.stream().map(
                    pessoa -> new ResponsePessoaDTO(
                            pessoa.getCodigoPessoa(),
                            pessoa.getNome(),
                            pessoa.getSobrenome(),
                            pessoa.getIdade(),
                            pessoa.getLogin(),
                            pessoa.getSenha(),
                            pessoa.getStatus(),
                            new ArrayList<>(),
                            pessoa.getCriadoEm(),
                            pessoa.getAtualizadoEm()
                    )).collect(Collectors.toList());
        }
    }

    public List<ResponsePessoaDTO> editarPessoa(UpdatePessoaDTO data) {
        Pessoa pessoaRecuperada = pessoaRepository.findById(data.codigoPessoa()).orElseThrow(() -> new CustomException("Código de pessoa não encontrado!", HttpStatus.NOT_FOUND, null));
        List<String> campoDuplicado = new ArrayList<>();

        Pessoa pessoaAVerificar = pessoaRepository.findByLogin(data.login()).orElseThrow(() -> new CustomException("Código de Pessoa não encontrado!", HttpStatus.CONFLICT, null));

        if (data.nome() != null) {
            pessoaRecuperada.setNome(data.nome().toLowerCase());
        }

        if (data.sobrenome() != null) {
            pessoaRecuperada.setSobrenome(data.sobrenome().toLowerCase());
        }

        if (data.idade() != null) {
            pessoaRecuperada.setIdade(data.idade());
        }

        if (data.login() != null) {
            if (pessoaAVerificar.getCodigoPessoa().equals(data.codigoPessoa())) {
                pessoaRecuperada.setLogin(data.login());
            } else {
                campoDuplicado.add("login");
            }
        }

        if (data.senha() != null) {
            pessoaRecuperada.setSenha(data.senha());
        }

        if (data.status() != null) {
            pessoaRecuperada.setStatus(data.status());
        }

        if (!data.enderecos().isEmpty()) {
            Set<Long> novosEnderecosIds = data.enderecos().stream()
                    .map(UpdateEnderecoDTO::codigoEndereco)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            for (UpdateEnderecoDTO endereco : data.enderecos()) {
                if (endereco.codigoEndereco() == null) {
                    Endereco enderecoCriado = new Endereco();

                    enderecoCriado.setCodigoPessoa(pessoaRecuperada);
                    enderecoCriado.setCodigoBairro(bairroService.recuperarBairroPorId(endereco.codigoBairro()));
                    enderecoCriado.setNomeRua(endereco.nomeRua());
                    enderecoCriado.setNumero(endereco.numero());
                    enderecoCriado.setComplemento(endereco.complemento());
                    enderecoCriado.setCep(endereco.cep());

                    pessoaRecuperada.getEnderecos().add(enderecoCriado);
                } else {
                    Endereco enderecoExistente = pessoaRecuperada.getEnderecos().stream()
                            .filter(endereco1 -> endereco1.getCodigoEndereco().equals(endereco.codigoEndereco()))
                            .filter(endereco1 -> endereco1.getCodigoPessoa().getCodigoPessoa().equals(endereco.codigoPessoa()))
                            .findFirst()
                            .orElseThrow(() -> new CustomException("Código do Endereço não encontrado", HttpStatus.NOT_FOUND, null));

                    enderecoExistente.setCodigoBairro(bairroService.recuperarBairroPorId(endereco.codigoBairro()));
                    enderecoExistente.setCodigoPessoa(pessoaRecuperada);
                    enderecoExistente.setNomeRua(endereco.nomeRua());
                    enderecoExistente.setNumero(endereco.numero());
                    enderecoExistente.setComplemento(endereco.complemento());
                    enderecoExistente.setCep(endereco.cep());
                }
            }
            pessoaRecuperada.getEnderecos().removeIf(endereco -> !novosEnderecosIds.contains(endereco.getCodigoEndereco()));
        } else {
            pessoaRecuperada.getEnderecos().clear();
        }

        if (!campoDuplicado.isEmpty()) {
            throw new CustomException("Existem campos que necessitam de atenção!", HttpStatus.CONFLICT, campoDuplicado);
        }

        pessoaRepository.save(pessoaRecuperada);

        return pessoaRepository.findAll().stream().map(
                pessoa -> new ResponsePessoaDTO(
                        pessoa.getCodigoPessoa(),
                        pessoa.getNome(),
                        pessoa.getSobrenome(),
                        pessoa.getIdade(),
                        pessoa.getLogin(),
                        pessoa.getSenha(),
                        pessoa.getStatus(),
                        new ArrayList<>(),
                        pessoa.getCriadoEm(),
                        pessoa.getAtualizadoEm()
                )).collect(Collectors.toList());

    }

    public Pessoa buscarPessoaPorId(Long codigoPessoa) {
        return pessoaRepository.findById(codigoPessoa).orElseThrow(() -> new CustomException("Código de pessoa não encontrado!", HttpStatus.NOT_FOUND, null));
    }

    private void validarValoresParametros(Map<String, Object> parametros) {
        List<String> camposErrados = new ArrayList<>();

        if (parametros.get("codigoPessoa") != null) {
            String codigoPessoa = (String) parametros.get("codigoPessoa");
            if (!codigoPessoa.matches("\\d+")) {
                camposErrados.add("codigoPessoa");
            }
        }

        if (parametros.get("login") != null && !(parametros.get("login") instanceof String)) {
            camposErrados.add("login");
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
