package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.venda.VendaCreateDTO;
import com.pds.ImobiGest.dto.venda.VendaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Venda", description = "API para gerenciamento de vendas")
public interface VendaControllerDoc {

    @Operation(summary = "Criar uma nova venda", description = "Cria uma nova venda no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Imobiliária não encontrada")
    })
    ResponseEntity<VendaDTO> create(@Valid @RequestBody VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar uma venda", description = "Atualiza os dados de uma venda existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Venda ou imobiliária não encontrada")
    })
    ResponseEntity<VendaDTO> update(
            @Parameter(description = "ID da venda a ser atualizada", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody VendaCreateDTO vendaCreateDTO
    ) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as vendas", description = "Retorna uma lista com todas as vendas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de vendas retornada com sucesso")
    List<VendaDTO> list();

    @Operation(summary = "Excluir uma venda", description = "Remove uma venda do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da venda a ser excluída", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;

    @Operation(summary = "Buscar venda por ID", description = "Retorna os dados de uma venda específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    ResponseEntity<VendaDTO> listById(
            @Parameter(description = "ID da venda a ser buscada", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;
}