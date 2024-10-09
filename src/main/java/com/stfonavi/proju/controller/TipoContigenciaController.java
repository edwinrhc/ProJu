package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.ITipoContigenciaService;
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
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("tipoContigencia")
@RequestMapping("/tipoContigencia")
@Controller
public class TipoContigenciaController {

    @Autowired
    private ITipoContigenciaService tipoContigenciaService;


    @GetMapping("/tipoContigenciaView")
    public String mostrar(@RequestParam(name ="page",defaultValue = "0")int page, Model model){

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<TipoContigencia> tipoContigencias = tipoContigenciaService.findAll(pageRequest);
        long total = tipoContigencias.getTotalElements();
        PageRender<TipoContigencia> pageRender = new PageRender<>("/tipoContigencia/tipoContigenciaView",tipoContigencias);

        model.addAttribute("titulo","Tipo Contigencia");
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("tipoContigencialList",tipoContigencias);
        model.addAttribute("page",pageRender);

        if(tipoContigencias.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }


        return  "uap/tipoContigencia/tipoContigenciaView";
    }




}
