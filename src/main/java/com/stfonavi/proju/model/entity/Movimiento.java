package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


import java.io.Serializable;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "uap_movimiento")
public class Movimiento implements Serializable {


    private static final long serialVersionUID = -3785586553443702528L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private long idMovimiento;

    @Column(name = "NOMBRE")
    @NotEmpty(message = "El campo nombre no puede estar en blanco")
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Column(name="FECHA")
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESO_JUDICIAL")
    private ProcesoJudiciales procesoJudicial;


//    @NotNull(message = "El campo contingencia no puede estar en blanco")
//    @Column(name = "ID_PROCESO_JUDICIAL", insertable = false, updatable = false)
//    private Long idProcesoJudicial;


    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CREACION", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ACTUALIZADA")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "CREADO_POR", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "ACTUALIZADO_POR")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
