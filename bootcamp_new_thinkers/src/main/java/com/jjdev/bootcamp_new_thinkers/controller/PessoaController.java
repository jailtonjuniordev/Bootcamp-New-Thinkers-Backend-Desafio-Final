package com.jjdev.bootcamp_new_thinkers.controller;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.CreatePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.ResponsePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.dto.UpdatePessoaDTO;
import com.jjdev.bootcamp_new_thinkers.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;


    @PostMapping
    public ResponseEntity<List<ResponsePessoaDTO>> cadastrarPessoa(@RequestBody @Valid CreatePessoaDTO data) {
        return new ResponseEntity<>(pessoaService.cadastrarPessoa(data), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<ResponsePessoaDTO>> editarPessoa(@RequestBody @Valid UpdatePessoaDTO data) {
        return new ResponseEntity<>(pessoaService.editarPessoa(data), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Object> listarPessoas(
            @RequestParam(required = false) String codigoPessoa,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String status
    ) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codigoPessoa", codigoPessoa);
        parametros.put("login", login);
        parametros.put("status", status);

        return new ResponseEntity<>(pessoaService.buscarPorParametros(parametros), HttpStatus.OK);
    }


}
