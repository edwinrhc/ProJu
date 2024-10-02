package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.UsuarioPerfil;

import java.util.List;

public interface IUsuarioPerfilService {

    void saveAll(List<UsuarioPerfil> usuarioPerfil);

    UsuarioPerfil findOne(char usrCId);
}
