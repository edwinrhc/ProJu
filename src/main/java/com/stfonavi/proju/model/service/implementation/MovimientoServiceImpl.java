package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.dao.IMovimientoDao;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.service.interfaces.IMovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoServiceImpl.class);

    @Autowired
    private IMovimientoDao movimientoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> findAll() {
        try {
            return movimientoDao.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener todos los movimientos", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Movimiento movimiento) {
        try {
            movimientoDao.save(movimiento);
        } catch (Exception e) {
            logger.error("Error al guardar el movimiento", e);
            throw e;
        }
    }

    @Override
    public Movimiento findOne(Long id) {
        try {
            return movimientoDao.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error al encontrar el movimiento con ID" + id, e);
            return null;
        }

    }

    @Override
    public List<Movimiento> obtenerMovimientos(long idProcesoJudicial) {
        try {
            return movimientoDao.findByProcesoJudicial_IdProcesoJudicial(idProcesoJudicial);
        } catch (Exception e) {
            logger.error("Error al encontrar el Proceso Judicial - id " + idProcesoJudicial, e);
            return null;
        }
    }

    @Override
    public List<MovimientoDetailDTO> getMovimientoDetailsByProcesoJudicialId(Long idProcesoJudicial) {
        return movimientoDao.findMovimientoDetailsByProcesoJudicialId(idProcesoJudicial);
    }
}
