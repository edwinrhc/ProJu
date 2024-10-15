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
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="proceso_judiciales")
public class ProcesoJudiciales implements Serializable {

    @Serial
    private static final long serialVersionUID = 4758150347274083148L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_expediente")
    private long id;


    @Column(name="n_expediente")
    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String numExpediente;

    @Column(name="materia")
    @NotEmpty(message="El campo no puede estar en blanco")
    private String materia;

    @Column(name="monto")
    @NotNull(message = "El campo no puede estar en blanco")
    private BigDecimal monto;

    @Column(name="tipo_moneda")
    @NotEmpty(message="El campo no puede estar en blanco")
    private String tipoMoneda;


    @Column(name="demandante")
    @NotEmpty(message="El campo no puede estar en blanco")
    private String demandante;

    @Column(name="demandado")
    @NotEmpty(message="El campo no puede estar en blanco")
    private String demandado;

    @Column(name="abogado")
    @NotEmpty(message="El campo no puede estar en blanco")
    private String abogado;

    private long idJuzgado;

    private long idContigencia;

    private long idMovimiento;

    

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_actualizada")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "creado_por", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "actualizado_por")
    private String updatedBy;

    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = new Date();
    }




}
