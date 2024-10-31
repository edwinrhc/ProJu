package com.stfonavi.proju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDetailDTO {

    private Long idMovimiento;
    private String nombre;
    private Long idEtapaProcesal;
    private String nombreEtapaProcesal;
    private Long idContingencia;
    private String nombreContingencia;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private Long idProcesoJudicial;

}
