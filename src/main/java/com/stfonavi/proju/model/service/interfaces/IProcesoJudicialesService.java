package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.dto.ProcesoJudicialesDTO;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProcesoJudicialesService {

    Page<ProcesoJudiciales> findAll(Pageable pageable);

    void save(ProcesoJudiciales procesoJudiciales);

    void updateProcesoJudicial(ProcesoJudicialesDTO procesoJudicialesDTO);

    ProcesoJudiciales findOne(long id);


}
