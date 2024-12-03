package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="SFSOP_USUARIO_FONAVI")
public class Usuario implements Serializable {


    private static final long serialVersionUID = 3163470740132323817L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SOPORTE.SFSQ_SIMA_IDUSUARIO", allocationSize = 1)
    @Column(name = "USR_C_ID")
    private Long USR_C_ID;

    @NotEmpty(message = "El campo usuario no puede estar en blanco")
    private String USR_D_USUARIO;

    private String USR_C_TIPO_DOCUMENTO = "01";

    @NotEmpty(message = "El campo número documento no puede estar en blanco")
//    @Size(max = 8, message = "El número maximo es 8 dígitos")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe contener exactamente 8 dígitos y deben ser números.")
    private String USR_D_NUMERO_DOCUMENTO;

    @NotEmpty(message = "El campo apellido paterno no puede estar en blanco")
    @Size(max = 50, message = "El apellido paterno no puede tener más de 50 caracteres.")
    private String USR_D_APELLIDO_PATERNO;

    @NotEmpty(message = "El campo apellido materno no puede estar en blanco")
    private String USR_D_APELLIDO_MATERNO;

    @NotEmpty(message = "El campo nombre no puede estar en blanco")
    private String USR_D_NOMBRES;

    @NotNull(message = "El campo estado no puede estar en blanco")
    private Integer USR_C_ESTADO_CUENTA;
    private Integer USR_C_USUARIO_CREACION = 1;

    @Temporal(TemporalType.DATE)
    private Date USR_F_FECHA_CREACION;

    @PrePersist
    public  void prePersist(){
        USR_F_FECHA_CREACION = new Date();
        USR_D_USUARIO = this.USR_D_USUARIO.toLowerCase();
    }
    @PreUpdate
    public  void preUpdate(){
        USR_D_USUARIO = this.USR_D_USUARIO.toLowerCase();
    }

    private Integer USR_C_USUARIO_MODIFICACION;

    private Date USR_F_FECHA_MODIFICACION;
    private String USR_D_IP_CREACION;
    private String USR_D_IP_MODIFICACION;


    @Column(name = "ARE_C_ID")
    @NotBlank(message = "El campo de área no puede estar en blanco")
    @Size(min=1, message = "El campo de área debe tener al menos 1  caracter")
    private String areaId;


    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "ARE_C_ID",insertable = false, updatable = false)
    private Area area;

    @NotEmpty(message = "El campo contraseña no puede estar en blanco")
    private String USR_D_CONTRASENA;


    @Transient // Este atributo no se persistirá en la base de datos
    private String confirmPassword;

    @NotEmpty(message = "El campo email no puede estar en blanco")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Por favor, introduce una dirección de correo electrónico válida.")
    private String USR_D_EMAIL;

    @NotEmpty(message = "El campo contrato no puede estar en blanco")
    private String USR_D_CONTRATO;

}
