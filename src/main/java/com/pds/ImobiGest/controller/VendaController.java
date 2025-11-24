package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.VendaControllerDoc;
import com.pds.ImobiGest.dto.venda.VendaCreateDTO;
import com.pds.ImobiGest.dto.venda.VendaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @GetMapping("/search")
    public ResponseEntity<Page<VendaDTO>> searchVendas(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) BigDecimal valorMin,
            @RequestParam(required = false) BigDecimal valorMax,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String formaPagamento,
            @RequestParam(required = false) Integer idImobiliaria,
            @RequestParam(required = false) Integer idProfissional,
            @RequestParam(required = false) String nomeComprador,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "dataVenda") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {

        Page<VendaDTO> result = vendaService.searchVendas(
                descricao, valorMin, valorMax, dataInicio, dataFim,
                formaPagamento, idImobiliaria, idProfissional, nomeComprador,
                page, limit, sortBy, sortOrder
        );

        return ResponseEntity.ok(result);
    }
}
