package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaCreateDTO;
import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ImobiliariaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/imobiliaria")
public class ImobiliariaController {

    private final ImobiliariaService imobiliariaService;

    @PostMapping
    public ResponseEntity<ImobiliariaDTO> create(@Valid @RequestBody ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(imobiliariaService.create(imobiliariaCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ImobiliariaDTO> list(){
        return imobiliariaService.list();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImobiliariaDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(imobiliariaService.update(id, imobiliariaCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        imobiliariaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImobiliariaDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(imobiliariaService.listById(id), HttpStatus.OK);
    }
}
