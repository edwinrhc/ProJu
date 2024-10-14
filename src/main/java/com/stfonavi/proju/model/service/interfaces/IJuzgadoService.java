package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Juzgado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJuzgadoService {

    Page<Juzgado> findAll(Pageable pageable);

    Page<Juzgado> buscarJuzgado(String juzgado, Pageable pageable);

    void save(Juzgado juzgado);

    Juzgado findOne(Long id);




}
