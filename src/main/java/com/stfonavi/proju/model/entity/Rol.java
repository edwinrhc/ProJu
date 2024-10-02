package com.stfonavi.proju.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SFSOP_ROL")
public class Rol implements Serializable {

    @Serial
    private static final long serialVersionUID = 8595330114925701711L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROL_ID")
    @SequenceGenerator(name = "SEQ_ROL_ID", sequenceName = "SEQ_ROL_ID", allocationSize = 1)
    @Column(name="ROL_C_ID")
    private Integer rolCID;

    @NotEmpty(message = "El campo descripción no puede estar en blanco")
    @Column(name="ROL_D_DESCRIPCION")
    private String rolDescripcion;

    @NotEmpty(message = "El campo estado no puede estar en blanco")
    @Column(name="ROL_C_ESTADO")
    private String rolCEstado;

    @Temporal(TemporalType.DATE)
    @Column(name="ROL_F_FECHA_CREACION")
    private Date rolFechaCreacion;

    @Column(name="ROL_C_USUARIO_CREACION")
    private Integer rolCUsuarioCreado;

    @Temporal(TemporalType.DATE)
    @Column(name="ROL_F_FECHA_MODIFICACION")
    private Date  rolFechaModificacion;

    @Column(name="ROL_C_USUARIO_MODIFICACION")
    private Integer rolCUsuarioModificado;

    // Creación de fecha
    @PrePersist
    public void prePersist() {
        rolFechaCreacion = new Date();
        rolFechaModificacion = new Date();
    }
    // Actualizacion de fecha
    @PreUpdate
    protected void onUpdate() {
        rolFechaModificacion = new Date();;

    }


}
