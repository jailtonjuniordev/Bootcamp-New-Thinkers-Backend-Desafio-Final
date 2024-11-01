package com.jjdev.bootcamp_new_thinkers.controller;

import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.CreateBairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.ResponseBairroDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.dto.UpdateBairroDTO;
import com.jjdev.bootcamp_new_thinkers.service.BairroService;
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
@RequestMapping("/bairro")
public class BairroController {

    private final BairroService bairroService;


    @PostMapping
    public ResponseEntity<List<ResponseBairroDTO>> cadastrarBairro(@RequestBody @Valid CreateBairroDTO data) {
        return new ResponseEntity<>(bairroService.cadastrarBairro(data), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<ResponseBairroDTO>> editarBairro(@RequestBody @Valid UpdateBairroDTO data) {
        return new ResponseEntity<>(bairroService.editarBairro(data), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> listarBairros(
            @RequestParam(required = false) String codigoBairro,
            @RequestParam(required = false) String codigoMunicipio,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String status
    ) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codigoMunicipio", codigoMunicipio);
        parametros.put("codigoBairro", codigoBairro);
        parametros.put("nome", nome);
        parametros.put("status", status);

        return new ResponseEntity<>(bairroService.listarBairros(parametros), HttpStatus.OK);
    }

}
