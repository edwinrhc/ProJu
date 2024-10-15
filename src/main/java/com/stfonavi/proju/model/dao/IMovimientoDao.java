package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMovimientoDao extends JpaRepository<Movimiento,Long> {


}
