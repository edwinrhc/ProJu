package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IMovimientoDao extends JpaRepository<Movimiento,Long> {


    List<Movimiento> findByProcesoJudicial_IdProcesoJudicial(Long idProcesoJudicial);



}
