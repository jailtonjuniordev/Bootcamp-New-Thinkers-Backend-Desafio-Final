package com.jjdev.bootcamp_new_thinkers.controller;

import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.CreateMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.ResponseMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.dto.UpdateMunicipioDTO;
import com.jjdev.bootcamp_new_thinkers.service.MunicipioService;
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
@RequestMapping("/municipio")
public class MunicipioController {

    private final MunicipioService municipioService;

    @PostMapping
    public ResponseEntity<List<ResponseMunicipioDTO>> cadastrarMunicipio(@RequestBody @Valid CreateMunicipioDTO data) {
        return new ResponseEntity<>(municipioService.cadastrarMunicipio(data), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<ResponseMunicipioDTO>> editarMunicipio(@RequestBody @Valid UpdateMunicipioDTO data) {
        return new ResponseEntity<>(municipioService.editarMunicipio(data), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> listarMunicipios(
            @RequestParam(required = false) String codigoMunicipio,
            @RequestParam(required = false) String codigoUF,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String status
    ) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codigoMunicipio", codigoMunicipio);
        parametros.put("codigoUF", codigoUF);
        parametros.put("nome", nome);
        parametros.put("status", status);

        return ResponseEntity.ok().body(municipioService.listarMunicipio(parametros));
    }
}
