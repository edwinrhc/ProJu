package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Movimiento;

import java.util.List;

public interface IMovimientoService {

    List<Movimiento> findAll();

    void save(Movimiento movimiento);

    Movimiento findOne(Long id);

}
