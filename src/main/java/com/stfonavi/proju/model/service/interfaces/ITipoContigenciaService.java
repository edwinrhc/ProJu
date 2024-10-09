package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.TipoContigencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoContigenciaService  {


    Page<TipoContigencia> findAll(Pageable pageable);

    void save(TipoContigencia tipoContigencia);

    TipoContigencia findOne(Long id);



}
