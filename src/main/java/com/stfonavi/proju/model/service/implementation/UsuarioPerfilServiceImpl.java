package com.stfonavi.proju.model.service.implementation;


import com.stfonavi.proju.model.dao.IUsuarioPerfilDao;
import com.stfonavi.proju.model.entity.UsuarioPerfil;
import com.stfonavi.proju.model.service.interfaces.IUsuarioPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioPerfilServiceImpl implements IUsuarioPerfilService {

    @Autowired
    private IUsuarioPerfilDao usuarioPerfilDao;


    /**
     * @param usuarioPerfil
     */
    @Override
    @Transactional
    public void saveAll(List<UsuarioPerfil> usuarioPerfil) {
        usuarioPerfilDao.saveAll(usuarioPerfil);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPerfil findOne(char usrCId) {
        return null;
      //  TODO: ERROR
        //usuarioPerfilDao.findById(usrCId).orElse(null);
    }


}
