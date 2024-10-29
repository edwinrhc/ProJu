package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IMovimientoDao extends JpaRepository<Movimiento,Long> {


    List<Movimiento> findByProcesoJudicial_IdProcesoJudicial(Long idProcesoJudicial);

    @Query("SELECT m.nombre, m.idEtapaProcesal, m.idContigencia FROM Movimiento m INNER JOIN m.procesoJudicial pj WHERE pj.idProcesoJudicial = :idProcesoJudicial")
    List<Object[]> findMovimientoDetailsByProcesoJudicialId(@Param("idProcesoJudicial") Long idProcesoJudicial);



}
