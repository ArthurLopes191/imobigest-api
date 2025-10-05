package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.comissao.ComissaoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoDTO;
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

@Tag(name = "Comissão", description = "API para gerenciamento de comissões de vendas")
public interface ComissaoControllerDoc {

    @Operation(summary = "Criar uma nova comissão", description = "Cria uma nova comissão vinculada a uma venda e profissional da mesma imobiliária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comissão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou profissional não pertence à mesma imobiliária da venda"),
            @ApiResponse(responseCode = "404", description = "Venda ou profissional não encontrado")
    })
    ResponseEntity<ComissaoDTO> create(@Valid @RequestBody ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar uma comissão", description = "Atualiza os dados de uma comissão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comissão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou profissional não pertence à mesma imobiliária da venda"),
            @ApiResponse(responseCode = "404", description = "Comissão, venda ou profissional não encontrado")
    })
    ResponseEntity<ComissaoDTO> update(
            @Parameter(description = "ID da comissão a ser atualizada", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody ComissaoCreateDTO comissaoCreateDTO
    ) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as comissões", description = "Retorna uma lista com todas as comissões cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de comissões retornada com sucesso")
    List<ComissaoDTO> list();

    @Operation(summary = "Excluir uma comissão", description = "Remove uma comissão do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comissão excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da comissão a ser excluída", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;

    @Operation(summary = "Buscar comissão por ID", description = "Retorna os dados de uma comissão específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comissão encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    })
    ResponseEntity<ComissaoDTO> listById(
            @Parameter(description = "ID da comissão a ser buscada", required = true)
            @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;
}