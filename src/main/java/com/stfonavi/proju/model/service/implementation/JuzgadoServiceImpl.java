package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.IJuzgadoDao;
import com.stfonavi.proju.model.entity.Juzgado;
import com.stfonavi.proju.model.service.interfaces.IJuzgadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JuzgadoServiceImpl implements IJuzgadoService {

    @Autowired
    private IJuzgadoDao juzgadoDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Juzgado> findAll(Pageable pageable) {
        return juzgadoDao.obtenerTodos(pageable);
    }

    @Override
    public List<Juzgado> ListarTodos() {
        return (List<Juzgado>) juzgadoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Juzgado> buscarJuzgado(String juzgado, Pageable pageable) {
        return juzgadoDao.buscarJuzgado(juzgado,pageable);
    }

    @Override
    public void save(Juzgado juzgado) {
        juzgadoDao.save(juzgado);
    }

    @Override
    @Transactional(readOnly = true)
    public Juzgado findOne(Long id) {
        return juzgadoDao.findById(id).orElse(null);
    }
}
