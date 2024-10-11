package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.TipoContigencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ITipoContigenciaDao  extends PagingAndSortingRepository<TipoContigencia, Long> {

    @Query(value = "SELECT t FROM TipoContigencia t ORDER BY t.id ASC ")
    Page<TipoContigencia> obtenerTodos(Pageable pageable);
}
