package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRolService {


    List<Rol> findAll();
    Page<Rol> findAll(Pageable pageable);

    List<Rol> ListarRolPorEstado();

    void save(Rol rol);

    Page<Rol> findByRolCID(Pageable pageable);

    Page<Rol> findByDescripcion(String nombre, Pageable pageable);

    Rol findOne(Integer rolCID);

    Rol findOneByRolCID(Integer rolCID);



    void delete(Integer rolCID);

    List<Rol> findByRolCID(Integer rolCID);

    void cambiarEstado(Integer rolCID);
}
