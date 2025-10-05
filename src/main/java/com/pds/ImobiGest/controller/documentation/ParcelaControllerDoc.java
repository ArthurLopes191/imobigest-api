package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.parcela.ParcelaCreateDTO;
import com.pds.ImobiGest.dto.parcela.ParcelaDTO;
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

@Tag(name = "Parcela", description = "API para gerenciamento de parcelas de pagamento")
public interface ParcelaControllerDoc {

    @Operation(summary = "Criar uma nova parcela", description = "Cria uma nova parcela de pagamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parcela criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    ResponseEntity<ParcelaDTO> create(@Valid @RequestBody ParcelaCreateDTO parcelaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar uma parcela", description = "Atualiza os dados de uma parcela existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parcela atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Parcela ou venda não encontrada")
    })
    ResponseEntity<ParcelaDTO> update(
            @Parameter(description = "ID da parcela a ser atualizada", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody ParcelaCreateDTO parcelaCreateDTO
    ) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as parcelas", description = "Retorna uma lista com todas as parcelas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de parcelas retornada com sucesso")
    List<ParcelaDTO> list();

    @Operation(summary = "Excluir uma parcela", description = "Remove uma parcela do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parcela excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Parcela não encontrada")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da parcela a ser excluída", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;

    @Operation(summary = "Buscar parcela por ID", description = "Retorna os dados de uma parcela específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parcela encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Parcela não encontrada")
    })
    ResponseEntity<ParcelaDTO> listById(
            @Parameter(description = "ID da parcela a ser buscada", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;
}