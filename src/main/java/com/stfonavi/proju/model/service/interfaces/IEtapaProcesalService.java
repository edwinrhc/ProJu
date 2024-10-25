package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.EtapaProcesal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEtapaProcesalService {


    Page<EtapaProcesal> findAll(Pageable pageable);

    List<EtapaProcesal> ListarTodos();

    void save(EtapaProcesal etapaProcesal);

    EtapaProcesal findOne(Long id);
}
