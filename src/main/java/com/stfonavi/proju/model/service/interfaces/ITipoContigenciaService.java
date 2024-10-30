package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.TipoContigencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITipoContigenciaService  {


    Page<TipoContigencia> findAll(Pageable pageable);

    List<TipoContigencia> findAll();

    void save(TipoContigencia tipoContigencia);

    TipoContigencia findOne(Long id);



}
