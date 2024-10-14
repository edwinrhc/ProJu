package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Juzgado;
import com.stfonavi.proju.model.entity.TipoContigencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IJuzgadoDao  extends PagingAndSortingRepository<Juzgado,Long> {

    @Query(value = "SELECT t FROM Juzgado t ORDER BY t.id ASC ")
    Page<Juzgado> obtenerTodos(Pageable pageable);

    @Query("SELECT j FROM Juzgado j WHERE LOWER(j.nombre) LIKE LOWER(CONCAT('%',:juzgado, '%')) OR  LOWER(j.lugar) LIKE LOWER(CONCAT('%',:juzgado, '%'))")
    Page<Juzgado> buscarJuzgado(@Param("juzgado")String juzgado, Pageable pageable);

}
