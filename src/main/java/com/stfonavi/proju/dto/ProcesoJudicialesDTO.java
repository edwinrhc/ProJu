package com.stfonavi.proju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcesoJudicialesDTO {

    private long idProcesoJudicial;
    private String numExpediente;
    private String materia;
    private BigDecimal monto;
    private String tipoMoneda;
    private String demandante;
    private String demandado;
    private Long idJuzgado;
    private String abogado;
    private String createdBy;
    private String updatedBy;
    private Long idEtapaProcesal;
    private String nombreEtapaProcesal;
    private Long idContingencia;
    private String nombreContingencia;
}
