package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IMovimientoDao extends JpaRepository<Movimiento, Long> {


    List<Movimiento> findByProcesoJudicial_IdProcesoJudicial(Long idProcesoJudicial);

//    @Query("SELECT new com.stfonavi.proju.dto.MovimientoDetailDTO(m.idMovimiento,m.nombre, m.idEtapaProcesal, m.idContigencia, m.createdAt, m.createdBy, m.updatedAt, m.updatedBy, pj.idProcesoJudicial) " +
//            "FROM Movimiento m INNER JOIN m.procesoJudicial pj WHERE pj.idProcesoJudicial = :idProcesoJudicial")
//    List<MovimientoDetailDTO> findMovimientoDetailsByProcesoJudicialId(@Param("idProcesoJudicial") Long idProcesoJudicial);
@Query("SELECT new  com.stfonavi.proju.dto.MovimientoDetailDTO(m.idMovimiento, m.nombre,FUNCTION('TO_CHAR', m.fecha, 'YYYY-MM-DD'), m.createdAt, m.createdBy, m.updatedAt, m.updatedBy, m.procesoJudicial.idProcesoJudicial) " +
        "FROM Movimiento m " +
        "WHERE m.procesoJudicial.idProcesoJudicial = :idProcesoJudicial")
List<MovimientoDetailDTO> findDetalleByProcesoJudicialId(@Param("idProcesoJudicial") Long idProcesoJudicial);

}
