package com.pds.ImobiGest.controller;


import com.pds.ImobiGest.controller.documentation.ParcelaControllerDoc;
import com.pds.ImobiGest.dto.parcela.ParcelaCreateDTO;
import com.pds.ImobiGest.dto.parcela.ParcelaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ParcelaService;
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
@RequestMapping("/parcela")
public class ParcelaController implements ParcelaControllerDoc {

    private final ParcelaService parcelaService;

    @PostMapping
    public ResponseEntity<ParcelaDTO> create(@Valid @RequestBody ParcelaCreateDTO parcelaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(parcelaService.create(parcelaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ParcelaCreateDTO parcelaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(parcelaService.update(id, parcelaCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<ParcelaDTO> list() {
        return parcelaService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        parcelaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(parcelaService.listById(id), HttpStatus.OK);
    }
}
