package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Rol;
import com.stfonavi.proju.model.entity.Usuario;
import com.stfonavi.proju.model.entity.UsuarioRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioRolService {

    List<UsuarioRol> findAll();

    Page<UsuarioRol> findAll(Pageable pageable);

    void save(UsuarioRol rol);

    void saveAll(List<UsuarioRol> usuarioRol);

    Page<UsuarioRol> findByUsrolCID(Pageable pageable); // Por ID

    Page<UsuarioRol> findByUsCID(String nombre, Pageable pageable); // Por Usuario

    UsuarioRol findOne(Integer usRolCID);

    void delete(Integer usRolCID);

    boolean existsByUsuarioAndRol(Usuario usuario, Rol rol);

    void deleteByUsrCID(Long usrCId);

    List<UsuarioRol> findByUsrCID(Long usrCId);

    void updateByUsrolEstado(Integer estado, Integer usrolCID);
}
