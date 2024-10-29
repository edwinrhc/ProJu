package com.stfonavi.proju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDetailDTO {

    private String nombre;
    private Long idEtapaProcesal;
    private Long idContingencia;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
