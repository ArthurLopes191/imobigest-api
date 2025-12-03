package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.venda.VendaCreateDTO;
import com.pds.ImobiGest.dto.venda.VendaDTO;
import com.pds.ImobiGest.entity.*;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ComissaoRepository;
import com.pds.ImobiGest.repository.ConfigComissaoRepository;
import com.pds.ImobiGest.repository.ProfissionalCargoRepository;
import com.pds.ImobiGest.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final ObjectMapper objectMapper;
    private final VendaRepository vendaRepository;
    private final ImobiliariaService imobiliariaService;
    private final ComissaoRepository comissaoRepository;
    private final ProfissionalCargoRepository profissionalCargoRepository;
    private final ConfigComissaoRepository configComissaoRepository;

    public VendaDTO create(VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        VendaEntity venda = new VendaEntity();
        venda.setDescricaoImovel(vendaCreateDTO.getDescricaoImovel());
        venda.setValorTotal(vendaCreateDTO.getValorTotal());
        venda.setDataVenda(vendaCreateDTO.getDataVenda());
        venda.setFormaPagamento(vendaCreateDTO.getFormaPagamento());
        venda.setQtdParcelas(vendaCreateDTO.getQtdParcelas());
        venda.setCompradorNome(vendaCreateDTO.getCompradorNome());
        venda.setCompradorContato(vendaCreateDTO.getCompradorContato());
        venda.setVendedorNome(vendaCreateDTO.getVendedorNome());
        venda.setVendedorContato(vendaCreateDTO.getVendedorContato());
        venda.setComissaoComprador(vendaCreateDTO.getComissaoComprador());
        venda.setComissaoVendedor(vendaCreateDTO.getComissaoVendedor());
        calcularComissaoImobiliaria(venda);

        if (vendaCreateDTO.getIdImobiliaria() != null) {
            ImobiliariaEntity imobiliaria = imobiliariaService.getById(vendaCreateDTO.getIdImobiliaria());
            if (imobiliaria == null) {
                throw new RegraDeNegocioException("Imobiliária não encontrada");
            }
            venda.setImobiliaria(imobiliaria);
        } else {
            throw new RegraDeNegocioException("Id da Imobiliária é obrigatório");
        }

        VendaEntity saved = vendaRepository.save(venda);
        criarComissoesAutomaticas(saved);
        return convertToDTO(saved);
    }

    private void criarComissoesAutomaticas(VendaEntity venda) {
        List<ProfissionalCargoEntity> profissionaisComComissaoAutomatica =
                profissionalCargoRepository.findByCargoComissaoAutomaticaAndImobiliaria(
                        venda.getImobiliaria().getId());

        profissionaisComComissaoAutomatica.forEach(profissionalCargo -> {
            boolean jaTemComissao = comissaoRepository.existsByVendaIdAndProfissionalId(
                    venda.getId(), profissionalCargo.getProfissional().getId());

            if (!jaTemComissao) {
                ComissaoEntity comissao = new ComissaoEntity();
                comissao.setVenda(venda);
                comissao.setProfissional(profissionalCargo.getProfissional());
                comissao.setTipoComissao("AUTOMATICA"); // Marca como automática

                configComissaoRepository
                        .findByImobiliariaIdAndCargoId(venda.getImobiliaria().getId(),
                                profissionalCargo.getCargo().getId())
                        .ifPresent(config -> {
                            comissao.setPercentual(config.getPercentual());
                            comissao.setValorComissao(venda.getValorComissaoImobiliaria()
                                    .multiply(config.getPercentual().divide(new BigDecimal(100))));
                        });

                comissaoRepository.save(comissao);
            }
        });
    }

    public VendaDTO update(Integer id, VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);
        venda.setDescricaoImovel(vendaCreateDTO.getDescricaoImovel());
        venda.setValorTotal(vendaCreateDTO.getValorTotal());
        venda.setDataVenda(vendaCreateDTO.getDataVenda());
        venda.setFormaPagamento(vendaCreateDTO.getFormaPagamento());
        venda.setQtdParcelas(vendaCreateDTO.getQtdParcelas());
        venda.setCompradorNome(vendaCreateDTO.getCompradorNome());
        venda.setCompradorContato(vendaCreateDTO.getCompradorContato());
        venda.setVendedorNome(vendaCreateDTO.getVendedorNome());
        venda.setVendedorContato(vendaCreateDTO.getVendedorContato());
        venda.setComissaoComprador(vendaCreateDTO.getComissaoComprador());
        venda.setComissaoVendedor(vendaCreateDTO.getComissaoVendedor());
        calcularComissaoImobiliaria(venda);

        if (vendaCreateDTO.getIdImobiliaria() != null) {
            ImobiliariaEntity imobiliaria = imobiliariaService.getById(vendaCreateDTO.getIdImobiliaria());
            if (imobiliaria == null) {
                throw new RegraDeNegocioException("Imobiliária não encontrada");
            }
            venda.setImobiliaria(imobiliaria);
        }
        VendaEntity updated = vendaRepository.save(venda);
        return convertToDTO(updated);

    }

    public List<VendaDTO> list() {
        return vendaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);

        List<ComissaoEntity> comissoes = comissaoRepository.findByVendaId(id);
        comissaoRepository.deleteAll(comissoes);

        vendaRepository.delete(venda);
    }

    public Page<VendaDTO> searchVendas(
            String descricao,
            BigDecimal valorMin,
            BigDecimal valorMax,
            LocalDate dataInicio,
            LocalDate dataFim,
            String formaPagamento,
            Integer idImobiliaria,
            Integer idProfissional,
            String nomeComprador,
            int page,
            int limit,
            String sortBy,
            String sortOrder) {

        Page<VendaEntity> vendas = vendaRepository.findVendasWithFilters(
                descricao,
                valorMin,
                valorMax,
                dataInicio,
                dataFim,
                formaPagamento,
                idImobiliaria,
                idProfissional,
                nomeComprador,
                sortBy,
                sortOrder,
                PageRequest.of(page, limit)
        );

        return vendas.map(this::convertToDTO);
    }

    public VendaDTO listById(Integer id) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);
        return convertToDTO(venda);
    }

    private void calcularComissaoImobiliaria(VendaEntity venda) {
        BigDecimal comissaoComprador = venda.getComissaoComprador() != null ? venda.getComissaoComprador() : BigDecimal.ZERO;
        BigDecimal comissaoVendedor = venda.getComissaoVendedor() != null ? venda.getComissaoVendedor() : BigDecimal.ZERO;
        BigDecimal comissaoTotal = comissaoComprador.add(comissaoVendedor);

        venda.setComissaoImobiliaria(comissaoTotal);

        // Calcula o valor monetário da comissão
        if (venda.getValorTotal() != null && comissaoTotal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal valorComissao = venda.getValorTotal()
                    .multiply(comissaoTotal.divide(new BigDecimal(100)));
            venda.setValorComissaoImobiliaria(valorComissao);
        } else {
            venda.setValorComissaoImobiliaria(BigDecimal.ZERO);
        }
    }

    public VendaEntity getById(Integer id) throws RegraDeNegocioException {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));
    }

    private VendaDTO convertToDTO(VendaEntity vendaEntity) {
        VendaDTO vendaDTO = objectMapper.convertValue(vendaEntity, VendaDTO.class);

        if (vendaEntity.getImobiliaria() != null) {
            vendaDTO.setIdImobiliaria(vendaEntity.getImobiliaria().getId());
        }

        return vendaDTO;
    }
}
