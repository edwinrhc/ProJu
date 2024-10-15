package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="juzgado")
public class Juzgado implements Serializable {


    @Serial
    private static final long serialVersionUID = 2993057363231848752L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juzgado")
    private long idJuzgado;

    @Column(name="nombre")
    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String nombre;

    @Column(name="lugar")
    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String lugar;

    @Column(name="estado")
    @NotNull(message = "El campo no puede ser nulo")
    private Boolean estado;

    // Auditoría
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
