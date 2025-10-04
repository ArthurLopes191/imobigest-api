package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.VendaControllerDoc;
import com.pds.ImobiGest.dto.venda.VendaCreateDTO;
import com.pds.ImobiGest.dto.venda.VendaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.VendaService;
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
@RequestMapping("/venda")
public class VendaController implements VendaControllerDoc {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaDTO> create(@Valid @RequestBody VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.create(vendaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.update(id, vendaCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<VendaDTO> list() {
        return vendaService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        vendaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.listById(id), HttpStatus.OK);
    }
}
