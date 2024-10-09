package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tipo_contingencia")
public class TipoContigencia implements Serializable {


    @Serial
    private static final long serialVersionUID = -3613359183470412925L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTINGENCIA")
    private long id;

    @Column(name="NOMBRE")
    @NotEmpty(message = "El campo  no puede estar en blanco")
    private String nombre;

    @Column(name="ESTADO")
    @NotNull(message = "El campo no puede ser nulo")
    private Boolean estado;

}
