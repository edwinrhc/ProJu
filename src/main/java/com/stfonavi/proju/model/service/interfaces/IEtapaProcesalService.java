package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.EtapaProcesal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEtapaProcesalService {


    Page<EtapaProcesal> findAll(Pageable pageable);

    void save(EtapaProcesal etapaProcesal);

    EtapaProcesal findOne(Long id);
}
