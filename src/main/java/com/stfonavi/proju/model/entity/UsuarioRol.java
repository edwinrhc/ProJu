package com.stfonavi.proju.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="SFSOP_USUARIO_ROL")
public class UsuarioRol  implements Serializable {


    @Serial
    private static final long serialVersionUID = 2143897781053641032L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO_ROL")
    @SequenceGenerator(name="SEQ_USUARIO_ROL", sequenceName = "SEQ_USUARIO_ROL", allocationSize = 1)
    @Column(name="USROL_C_ID")
    private Integer usrolCID;

    @Column(name ="USR_C_ID")
    private Long usrCID;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
//    @JoinColumn(name="USR_C_ID",insertable = false,updatable = false)
    @JoinColumn(name = "USR_C_ID", referencedColumnName = "USR_C_ID", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name="ROL_C_ID")
    private Integer rolCID;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="ROL_C_ID",insertable = false,updatable = false)
    private Rol rol;

    @NotNull(message = "EL estado es requerido")
    @Column(name="USROL_C_ESTADO")
    private Integer usrolEstado;

    @Column(name="USROL_F_FECHA_CREACION")
    private Date usrolFechaCreacion;

    @Column(name="USROL_C_USUARIO_CREACION")
    private Integer usrolUsuarioCreacion;

    @Column(name="USROL_F_FECHA_MODIFICACION")
    private Date usrolFechaModificacion;

    @Column(name="USROL_C_USUARIO_MODIFICACION")
    private Integer usrolUsuarioModificacion;

    // Creación de fecha
    @PrePersist
    public void prePersist() {
        usrolFechaCreacion = new Date();
        usrolFechaModificacion = new Date();
    }
    // Actualizacion de fecha
    @PreUpdate
    protected void onUpdate() {
        usrolFechaModificacion = new Date();;
    }
}
