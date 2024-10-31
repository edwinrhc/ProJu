package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.IEtapaProcesalDao;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.service.interfaces.IEtapaProcesalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EtapaProcesalImpl implements IEtapaProcesalService {

    @Autowired
    private IEtapaProcesalDao etapaProcesalDao;
    @Override
    public Page<EtapaProcesal> findAll(Pageable pageable) {
        return  etapaProcesalDao.obtenerTodos(pageable);
    }

    @Override
    @Transactional
    public void save(EtapaProcesal etapaProcesal) {
        etapaProcesalDao.save(etapaProcesal);
    }

    @Override
    @Transactional(readOnly = true)
    public EtapaProcesal findOne(Long id) {
        return etapaProcesalDao.findById(id).orElse(null);
    }
}
