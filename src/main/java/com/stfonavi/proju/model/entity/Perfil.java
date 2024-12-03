package com.stfonavi.proju.model.entity;

import com.stfonavi.proju.util.helper.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SFSOP_PERFIL")
public class Perfil implements Serializable {


    private static final long serialVersionUID = 8260151210085662720L;

    @Id
    private Long PRF_C_ID;
    @NotEmpty(message = "El campo usuario no puede estar en blanco")
    private String PRF_D_DESCRIPCION_ABREVIADA;
    @NotEmpty(message = "El campo usuario no puede estar en blanco")
    private String PRF_D_DESCRIPCION;

    @NotEmpty(message = "El campo estado no puede estar en blanco")
    private String PRF_C_ESTADO_PERFIL;
    private Integer PRF_C_USARIO_CREACION = 55;
    @Temporal(TemporalType.DATE)
    private Date PRF_F_FECHA_CREACION;
    private String PRF_D_IP_CREACION;
    private Long PRF_C_USUARIO_MODIFICACION;
    private Date PRF_F_FECHA_MODIFICACION;
    private String PRF_D_IP_MODIFICACION;

    @PrePersist
    public void prePersist() {
        PRF_F_FECHA_CREACION = new Date();
        PRF_D_IP_CREACION = Constantes.obtenerIP();
    }
}
