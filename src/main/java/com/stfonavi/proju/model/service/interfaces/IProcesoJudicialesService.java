package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProcesoJudicialesService {

    Page<ProcesoJudiciales> findAll(Pageable pageable);

    void save(ProcesoJudiciales procesoJudiciales);

    ProcesoJudiciales findOne(long id);
}
