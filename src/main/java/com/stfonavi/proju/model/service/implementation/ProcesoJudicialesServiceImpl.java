package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.IProcesoJudicialesDao;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import com.stfonavi.proju.model.service.interfaces.IProcesoJudicialesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProcesoJudicialesServiceImpl implements IProcesoJudicialesService {

    private static final Logger logger = LoggerFactory.getLogger(ProcesoJudicialesServiceImpl.class);

    @Autowired
    private IProcesoJudicialesDao procesoJudicialesDao;

    @Override
    @Transactional(readOnly = true)
    public Page<ProcesoJudiciales> findAll(Pageable pageable) {

        try {
            return procesoJudicialesDao.obtenerTodos(pageable);
        } catch (Exception e) {
            logger.error("Error al obtener todos los procesos Judiciales",e.getMessage());
            throw e;
        }

    }

    @Override
    public void save(ProcesoJudiciales procesoJudiciales) {

        try{
            procesoJudicialesDao.save(procesoJudiciales);
        }catch (Exception e){
            logger.error("Error al guardar el proceso judicial",e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProcesoJudiciales findOne(long id) {
        try{
            return procesoJudicialesDao.findById(id).orElse(null);
        }catch (Exception e){
            logger.error("Error al encontrar el proceso judicial con ID" + id,e.getMessage());
            throw e;
        }
    }

}
