package com.jjdev.bootcamp_new_thinkers.controller;

import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.CreateUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.ResponseUFDTO;
import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.dto.UpdateUFDTO;
import com.jjdev.bootcamp_new_thinkers.service.UFService;
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
@RequestMapping("/uf")
public class UFController {

    private final UFService ufService;

    @PostMapping()
    public ResponseEntity<List<ResponseUFDTO>> cadastrarUF(@RequestBody @Valid CreateUFDTO data) {
        return new ResponseEntity<>(ufService.cadastrarUF(data), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<List<ResponseUFDTO>> editarUF(@RequestBody @Valid UpdateUFDTO data) {
        return new ResponseEntity<>(ufService.editarUF(data), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Object> listarUFs(
            @RequestParam(required = false) String codigoUF,
            @RequestParam(required = false) String sigla,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String status
    ) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codigoUF", codigoUF);
        parametros.put("sigla", sigla);
        parametros.put("nome", nome);
        parametros.put("status", status);

        return new ResponseEntity<>(ufService.listarUF(parametros), HttpStatus.OK);
    }
}
