package com.stfonavi.proju.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="etapa_procesal")
public class EtapaProcesal implements Serializable {

    @Serial
    private static final long serialVersionUID = -5442316589103608131L;

    @Id
    @Column(name = "ID_ETAPA")
    private long id;

    @Column(name="NOMBRE")
    private String nombre;

    @Column(name="ESTADO")
    private boolean estado;
}
