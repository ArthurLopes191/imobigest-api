package com.pds.ImobiGest.dto.parcela;

import com.pds.ImobiGest.enums.StatusParcela;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParcelaCreateDTO {

    private Integer numeroParcela;
    private BigDecimal valorParcela;
    private LocalDateTime dataVencimento;
    private StatusParcela status;
    private Integer idVenda;
}
