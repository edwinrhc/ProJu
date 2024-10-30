package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.entity.Movimiento;

import java.util.List;

public interface IMovimientoService {

    List<Movimiento> findAll();

    void save(Movimiento movimiento);

    void guardarMovimiento(MovimientoDetailDTO movimientoDetailDTO);

    Movimiento findOne(Long id);

    List<Movimiento> obtenerMovimientos(long idProcesoJudicial);

    List<MovimientoDetailDTO> getMovimientoDetailsByProcesoJudicialId(Long idProcesoJudicial);

}
