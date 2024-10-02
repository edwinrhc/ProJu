package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPerfilService {


    List<Perfil> findAll();
    Page<Perfil> findAll(Pageable pageable);

    Perfil findOne(Long  PRF_C_ID);

    void delete(Long  PRF_C_ID);

    void save(Perfil perfil);

    void guardarNuevoTipoEquipo(Perfil perfil);
}
