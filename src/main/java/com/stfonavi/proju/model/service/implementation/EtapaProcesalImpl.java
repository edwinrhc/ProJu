package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.IEtapaProcesalDao;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.service.interfaces.IEtapaProcesalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EtapaProcesalImpl implements IEtapaProcesalService {

    @Autowired
    private IEtapaProcesalDao etapaProcesalDao;
    @Override
    public Page<EtapaProcesal> findAll(Pageable pageable) {
        return  etapaProcesalDao.obtenerTodos(pageable);
    }
}
