package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.profissional.ProfissionalCreateDTO;
import com.pds.ImobiGest.dto.profissional.ProfissionalDTO;
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

@Tag(name = "Profissional", description = "API para gerenciamento de profissionais")
public interface ProfissionalControllerDoc {

    @Operation(summary = "Criar profissional", description = "Cria um novo profissional no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    ResponseEntity<ProfissionalDTO> create(@Valid @RequestBody ProfissionalCreateDTO profissionalCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar profissional", description = "Atualiza os dados de um profissional existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    ResponseEntity<ProfissionalDTO> update(
            @Parameter(description = "ID do profissional", required = true) @PathVariable("id") Integer id,
            @Valid @RequestBody ProfissionalCreateDTO profissionalCreateDTO
    ) throws RegraDeNegocioException;

    @Operation(summary = "Listar profissionais", description = "Retorna lista de todos os profissionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso")
    })
    List<ProfissionalDTO> list();

    @Operation(summary = "Excluir profissional", description = "Exclui um profissional do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "422", description = "Não é possível excluir o profissional")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do profissional", required = true) @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;

    @Operation(summary = "Buscar profissional por ID", description = "Retorna um profissional específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    ResponseEntity<ProfissionalDTO> listById(
            @Parameter(description = "ID do profissional", required = true) @PathVariable("id") Integer id
    ) throws RegraDeNegocioException;
}