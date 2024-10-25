package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.EtapaProcesal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IEtapaProcesalDao extends PagingAndSortingRepository<EtapaProcesal, Long> {

    @Query(value="SELECT e FROM EtapaProcesal  e ORDER BY e.idEtapa ASC ")
    Page<EtapaProcesal>  obtenerTodos(Pageable pageable);


}
