package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IProcesoJudicialesDao extends PagingAndSortingRepository<ProcesoJudiciales, Long> {

    @Query(value = "SELECT p FROM ProcesoJudiciales p ORDER BY p.idProcesoJudicial ASC ")
    Page<ProcesoJudiciales> obtenerTodos(Pageable pageable);

    @Query("SELECT p FROM ProcesoJudiciales p WHERE LOWER(p.numExpediente) LIKE LOWER(CONCAT('%',:procesoJudicial, '%'))")
    Page<ProcesoJudiciales> buscarProcesoJudicial(@Param("procesoJudicial")String procesoJudicial, Pageable pageable);
}
