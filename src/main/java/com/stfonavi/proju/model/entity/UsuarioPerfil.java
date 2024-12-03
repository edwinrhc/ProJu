package com.stfonavi.proju.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "SFSOP_USUARIO_PERFIL")
public class UsuarioPerfil implements Serializable {



    private static final long serialVersionUID = 7331292432758149239L;

    @Id
    @Column(name = "USR_C_ID")
    private Long usrCId;

    @Column(name = "PRC_C_ID")
    private char prfCId;

    @Column(name = "USPF_C_USUARIO_CREACION")
    private Integer uspfCUsuarioCreacion;

    @Column(name = "USPF_F_FECHA_CREACION")
    private Date uspfFechaCreacion;

    @Column(name = "USPF_D_IP_CREACION")
    private String uspfDIpCreacion;

    @Column(name = "USPF_C_USUARIO_MODIFICACION")
    private Integer uspfCUsuarioModificacion;

    @Column(name = "USPF_F_FECHA_MODIFICACION")
    private Date uspfFechaModificacion;

    @Column(name = "USPF_D_IP_MODIFICACION")
    private String ipModificacion;

    @Column(name = "USPF_C_ESTADO")
    private Integer uspfCEstado;


}
