package com.stfonavi.proju.model.service.implementation;


import com.stfonavi.proju.model.dao.IUsuarioRolDao;
import com.stfonavi.proju.model.entity.Rol;
import com.stfonavi.proju.model.entity.Usuario;
import com.stfonavi.proju.model.entity.UsuarioRol;
import com.stfonavi.proju.model.service.interfaces.IUsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioRolServiceImpl implements IUsuarioRolService {

    @Autowired
    private IUsuarioRolDao usuarioRolDao;

    public UsuarioRolServiceImpl(IUsuarioRolDao usuarioRolDao) {
        this.usuarioRolDao = usuarioRolDao;
    }

    @Override
    public List<UsuarioRol> findAll() {
        return usuarioRolDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRol> findAll(Pageable pageable) {
        return usuarioRolDao.findAll(pageable);
    }


    @Override
    @Transactional
    public void save(UsuarioRol rol) {
        usuarioRolDao.save(rol);
    }

    @Override
    @Transactional
    public void saveAll(List<UsuarioRol> usuarioRol) {
        usuarioRolDao.saveAll(usuarioRol);
    }


    @Override
    public UsuarioRol findOne(Integer usrolCID) {
        return usuarioRolDao.findById(usrolCID).orElse(null);
    }

    @Override
    public Page<UsuarioRol> findByUsrolCID(Pageable pageable) {
        return usuarioRolDao.findByUsrolCID(pageable);
    }

    @Override
    public Page<UsuarioRol> findByUsCID(String nombre, Pageable pageable) {
        return  usuarioRolDao.findByUsrCID(nombre,pageable);
    }

    @Override
    public void delete(Integer usRolCID) {
        usuarioRolDao.deleteById(usRolCID);
    }



    @Override
    public boolean existsByUsuarioAndRol(Usuario usuario, Rol rol) {
        return usuarioRolDao.existsByUsuarioAndRol(usuario,rol);
    }

    @Override
    public void deleteByUsrCID(Long usrCId) {
        usuarioRolDao.deleteByUsrCID(usrCId);
    }

    @Override
    public List<UsuarioRol> findByUsrCID(Long usrCId) {
        return usuarioRolDao.findByUsrCID(usrCId);
    }

    @Override
    public void updateByUsrolEstado(Integer estado,Integer usrolCID) {
        usuarioRolDao.updateByUsrolEstado(estado,usrolCID);
    }


}
