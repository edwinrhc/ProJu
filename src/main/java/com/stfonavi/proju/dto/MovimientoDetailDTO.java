package com.stfonavi.proju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDetailDTO {

    private Long idMovimiento;
    private String nombre;
    private String fecha;

    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private Long idProcesoJudicial;

    public MovimientoDetailDTO(Long idMovimiento, String nombre, Date fecha /* otros parámetros */) {
        this.idMovimiento = idMovimiento;
        this.nombre = nombre;

        // Convertir el Date a String en el formato deseado
        if (fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.fecha = sdf.format(fecha);
        } else {
            this.fecha = null;
        }
    }
}
