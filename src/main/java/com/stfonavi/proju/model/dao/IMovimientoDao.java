package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IMovimientoDao extends JpaRepository<Movimiento,Long> {


    List<Movimiento> findByProcesoJudicial_IdProcesoJudicial(Long idProcesoJudicial);

    @Query("SELECT new com.stfonavi.proju.dto.MovimientoDetailDTO(m.nombre, m.idEtapaProcesal, m.idContigencia, m.createdAt, m.createdBy, m.updatedAt, m.updatedBy) " +
            "FROM Movimiento m INNER JOIN m.procesoJudicial pj WHERE pj.idProcesoJudicial = :idProcesoJudicial")
    List<MovimientoDetailDTO> findMovimientoDetailsByProcesoJudicialId(@Param("idProcesoJudicial") Long idProcesoJudicial);




}
