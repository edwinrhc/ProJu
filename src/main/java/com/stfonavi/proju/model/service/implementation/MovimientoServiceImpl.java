package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.dao.IEtapaProcesalDao;
import com.stfonavi.proju.model.dao.IMovimientoDao;
import com.stfonavi.proju.model.dao.IProcesoJudicialesDao;
import com.stfonavi.proju.model.dao.ITipoContigenciaDao;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.IMovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoServiceImpl.class);

    @Autowired
    private IMovimientoDao movimientoDao;

    @Autowired
    private IProcesoJudicialesDao procesoJudicialDao;

    @Autowired
    private IEtapaProcesalDao etapaProcesalDao;

    @Autowired
    private ITipoContigenciaDao tipoContigenciaDao;


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
    @Transactional
    public void guardarMovimiento(MovimientoDetailDTO movimientoDetailDTO) {

        try {
            Movimiento movimiento = new Movimiento();
            movimiento.setNombre(movimientoDetailDTO.getNombre());

            if (movimientoDetailDTO.getFecha() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(movimientoDetailDTO.getFecha());
                movimiento.setFecha(fecha);
            }
            movimiento.setIdEtapaProcesal(movimientoDetailDTO.getIdEtapaProcesal());
            movimiento.setIdContigencia(movimientoDetailDTO.getIdContingencia());
//        movimiento.setIdProcesoJudicial(movimientoDetailDTO.getIdProcesoJudicial());
            // Obtener y establecer la relación con ProcesoJudiciales
            ProcesoJudiciales procesoJudicial = procesoJudicialDao.findById(movimientoDetailDTO.getIdProcesoJudicial())
                    .orElseThrow(() -> new IllegalArgumentException("Proceso Judicial no encontrado"));

            movimiento.setProcesoJudicial(procesoJudicial);

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.isAuthenticated()){
                String username = authentication.getName();
                movimiento.setCreatedBy(username);
            }else{
                throw new RuntimeException("Usuario no autenticado");
            }



            movimientoDao.save(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el movimiento: " + e.getMessage());
        }


    }

    @Override
    @Transactional
    public void updateMovimiento(MovimientoDetailDTO movimientoDetailDTO) {

        try {
            Movimiento movimiento = movimientoDao.findById(movimientoDetailDTO.getIdMovimiento())
                    .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado con ID: " + movimientoDetailDTO.getIdMovimiento()));
            // Actualizar los campos del movimiento
            movimiento.setNombre(movimientoDetailDTO.getNombre());
            if (movimientoDetailDTO.getFecha() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(movimientoDetailDTO.getFecha());
                movimiento.setFecha(fecha);
            }
            movimiento.setIdEtapaProcesal(movimientoDetailDTO.getIdEtapaProcesal());
            movimiento.setIdContigencia(movimientoDetailDTO.getIdContingencia());

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.isAuthenticated()){
                String username = authentication.getName();
                movimiento.setUpdatedBy(username);
            }else{
                throw new RuntimeException("Usuario no autenticado");
            }


            movimientoDao.save(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el movimiento: " + e.getMessage());
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
        List<Movimiento> movimientos = movimientoDao.findByProcesoJudicial_IdProcesoJudicial(idProcesoJudicial);
        List<MovimientoDetailDTO> movimientoDetails = new ArrayList<>();

        for (Movimiento movimiento : movimientos) {
            MovimientoDetailDTO dto = new MovimientoDetailDTO();
            dto.setIdMovimiento(movimiento.getIdMovimiento());
            dto.setNombre(movimiento.getNombre());
            if (movimiento.getFecha() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaFormateada = dateFormat.format(movimiento.getFecha());
                dto.setFecha(fechaFormateada);
            }

            dto.setIdEtapaProcesal(movimiento.getIdEtapaProcesal());
            dto.setIdContingencia(movimiento.getIdContigencia());

            // Obtener y establecer los nombres
            Optional<EtapaProcesal> etapa = etapaProcesalDao.findById(movimiento.getIdEtapaProcesal());
            etapa.ifPresent(e -> dto.setNombreEtapaProcesal(e.getNombre()));


            Optional<TipoContigencia> contigencia = tipoContigenciaDao.findById(movimiento.getIdContigencia());
            contigencia.ifPresent(c -> dto.setNombreContingencia(c.getNombre()));

            dto.setCreatedAt(movimiento.getCreatedAt());
            dto.setCreatedBy(movimiento.getCreatedBy());
            dto.setUpdatedAt(movimiento.getUpdatedAt());
            dto.setUpdatedBy(movimiento.getUpdatedBy());

            dto.setIdProcesoJudicial(movimiento.getProcesoJudicial().getIdProcesoJudicial());
            movimientoDetails.add(dto);

        }
        return movimientoDetails;
    }
}
