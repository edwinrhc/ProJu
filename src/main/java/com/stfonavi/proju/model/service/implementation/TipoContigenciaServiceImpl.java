package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.ITipoContigenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TipoContigenciaServiceImpl implements ITipoContigenciaService {


    @Override
    public Page<TipoContigencia> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void save(TipoContigencia tipoContigencia) {

    }

    @Override
    public TipoContigencia findOne(Long id) {
        return null;
    }
}
