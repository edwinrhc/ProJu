package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.service.interfaces.IEtapaProcesalService;
import com.stfonavi.proju.util.helper.Constantes;
import com.stfonavi.proju.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/etapaProcesal")
public class EtapaProcesalController {

    @Autowired
    private IEtapaProcesalService etapaProcesalService;


    @GetMapping("/etapaProcesalView")
    public String mostrarEtapa(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 2);
        Page<EtapaProcesal> etapaProcesal = etapaProcesalService.findAll(pageRequest);
        long total = etapaProcesal.getTotalElements();
        PageRender<EtapaProcesal> pageRender = new PageRender<>("/etapaProcesal/etapaProcesalView", etapaProcesal);

        model.addAttribute("titulo","Listado de etapas procesales");
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("etapaProcesal",etapaProcesal);
        model.addAttribute("page",pageRender);

        if(etapaProcesal.isEmpty()){
            model.addAttribute("mensaje","No se encontraron usuarios");
        }

        return "uap/etapaProcesal/etapaProcesalView";
    }

    @GetMapping(value = "/get")
    public String mostrarFormEtapa(Map<String, Object> model) {

        model.put("boton", "Registro nuevo");

        return "uap/etapaProcesal/formEtapaProcesal";
    }


}
