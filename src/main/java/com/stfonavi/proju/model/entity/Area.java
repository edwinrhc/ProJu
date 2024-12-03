package com.stfonavi.proju.model.entity;

import com.stfonavi.proju.util.helper.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="SFSOP_AREA")
public class Area implements Serializable {

    private static final long serialVersionUID = 3163470740130853817L;

    @Id
    private String ARE_C_ID;

    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String ARE_D_DESCRIPCION_ABREVIADA;

    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String ARE_D_DESCRIPCION;

    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String ARE_C_ESTADO_AREA;
    private Integer ARE_C_USUARIO_CREACION = 55;

    @Temporal(TemporalType.DATE)
    private Date ARE_F_FECHA_CREACION;


    @PrePersist
    public  void prePersist(){
        ARE_F_FECHA_CREACION = new Date();
        ARE_D_IP_CREACION = Constantes.obtenerIP();
    }

    private String ARE_D_IP_CREACION;
    private Integer ARE_C_USUARIO_MODIFICACION;
    private Date ARE_F_FECHA_MODIFICACION;
    private String ARE_D_IP_MODIFICACION;
    private String ARE_C_ID_PADRE;
    private String LOC_C_ID;




}
