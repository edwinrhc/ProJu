package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "uap_movimiento")
public class Movimiento implements Serializable {

    @Serial
    private static final long serialVersionUID = -3785586553443702528L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private long idMovimiento;

    @Column(name = "NOMBRE")
    @NotEmpty(message = "El campo nombre no puede estar en blanco")
    private String nombre;

    @NotNull(message = "El campo etapa procesal no puede estar en blanco")
    @Column(name = "ID_ETAPA")
    private Long idEtapaProcesal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ETAPA", insertable = false, updatable = false)
    private EtapaProcesal etapaProcesal;

    @NotNull(message = "El campo contingencia no puede estar en blanco")
    @Column(name = "ID_CONTINGENCIA")
    private Long idContigencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTINGENCIA", insertable = false, updatable = false)
    private TipoContigencia tipoContigencia;

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
