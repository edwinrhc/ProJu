package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.ITipoContigenciaDao;
import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.ITipoContigenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoContigenciaServiceImpl implements ITipoContigenciaService {

    @Autowired
    private ITipoContigenciaDao tipoContigenciaDao;

    @Override
    public Page<TipoContigencia> findAll(Pageable pageable) {
        return tipoContigenciaDao.obtenerTodos(pageable);
    }

    @Override
    public List<TipoContigencia> findAll() {
        return (List<TipoContigencia>) tipoContigenciaDao.findAll();
    }

    @Override
    public void save(TipoContigencia tipoContigencia) {
            tipoContigenciaDao.save(tipoContigencia);
    }

    @Override
    public TipoContigencia findOne(Long id) {
        return tipoContigenciaDao.findById(id).orElse(null);
    }
}
