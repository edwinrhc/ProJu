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
@Table(name="uap_movimiento")
public class Movimiento implements Serializable {

    @Serial
    private static final long serialVersionUID = -3785586553443702528L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private long idMovimiento;

    @Column(name="NOMBRE")
    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String nombre;

    @NotNull(message = "El campo de área no puede estar en blanco")
    @Column(name = "ID_ETAPA")
    private long idEtapaProcesal;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ETAPA", referencedColumnName = "ID_ETAPA", insertable = false, updatable = false)
    private EtapaProcesal etapaProcesal;


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
