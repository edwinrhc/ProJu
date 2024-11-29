package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.dto.ProcesoJudicialesDTO;
import com.stfonavi.proju.model.dao.IProcesoJudicialesDao;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.IProcesoJudicialesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = false)
    public void save(ProcesoJudiciales procesoJudiciales) {

        try{
            procesoJudicialesDao.save(procesoJudiciales);
        }catch (Exception e){
            logger.error("Error al guardar el proceso judicial",e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void guardarProcesoJudicial(ProcesoJudicialesDTO procesoJudicialesDTO) {

        ProcesoJudiciales procesoJudiciales = new ProcesoJudiciales();
        procesoJudiciales.setNumExpediente(procesoJudicialesDTO.getNumExpediente());
        procesoJudiciales.setMateria(procesoJudicialesDTO.getMateria());
        procesoJudiciales.setTipoMoneda(procesoJudicialesDTO.getTipoMoneda());
        procesoJudiciales.setMonto(procesoJudicialesDTO.getMonto());
        procesoJudiciales.setDemandante(procesoJudicialesDTO.getDemandante());
        procesoJudiciales.setDemandado(procesoJudicialesDTO.getDemandado());
        procesoJudiciales.setAbogado(procesoJudicialesDTO.getAbogado());
        procesoJudiciales.setIdJuzgado(procesoJudicialesDTO.getIdJuzgado());

        procesoJudiciales.setIdEtapaProcesal(procesoJudicialesDTO.getIdEtapaProcesal());
        procesoJudiciales.setIdContigencia(procesoJudicialesDTO.getIdContingencia());

        procesoJudicialesDao.save(procesoJudiciales);
    }

    @Override
    @Transactional
    public void updateProcesoJudicial(ProcesoJudicialesDTO procesoJudicialesDTO) {

        ProcesoJudiciales procesoJudiciales = procesoJudicialesDao.findById(procesoJudicialesDTO.getIdProcesoJudicial())
                .orElseThrow(() -> new EntityNotFoundException("Proceso judicial no encontrado con ID: " + procesoJudicialesDTO.getIdProcesoJudicial()));

        procesoJudiciales.setNumExpediente(procesoJudicialesDTO.getNumExpediente());
        procesoJudiciales.setMateria(procesoJudicialesDTO.getMateria());
        procesoJudiciales.setTipoMoneda(procesoJudicialesDTO.getTipoMoneda());
        procesoJudiciales.setMonto(procesoJudicialesDTO.getMonto());
        procesoJudiciales.setDemandante(procesoJudicialesDTO.getDemandante());
        procesoJudiciales.setDemandado(procesoJudicialesDTO.getDemandado());
        procesoJudiciales.setAbogado(procesoJudicialesDTO.getAbogado());
        procesoJudiciales.setIdJuzgado(procesoJudicialesDTO.getIdJuzgado());

        procesoJudiciales.setIdEtapaProcesal(procesoJudicialesDTO.getIdEtapaProcesal());
        procesoJudiciales.setIdContigencia(procesoJudicialesDTO.getIdContingencia());

        procesoJudicialesDao.save(procesoJudiciales);


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

/*
        dto.setIdEtapaProcesal(movimiento.getIdEtapaProcesal());
        dto.setIdContingencia(movimiento.getIdContigencia());

// Obtener y establecer los nombres
Optional<EtapaProcesal> etapa = etapaProcesalDao.findById(movimiento.getIdEtapaProcesal());
            etapa.ifPresent(e -> dto.setNombreEtapaProcesal(e.getNombre()));


Optional<TipoContigencia> contigencia = tipoContigenciaDao.findById(movimiento.getIdContigencia());
            contigencia.ifPresent(c -> dto.setNombreContingencia(c.getNombre()));*/
