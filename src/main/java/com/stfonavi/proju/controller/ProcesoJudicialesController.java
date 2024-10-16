package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.service.interfaces.IProcesoJudicialesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/procesoJudiciales")
public class ProcesoJudicialesController {

    @Autowired
    private IProcesoJudicialesService procesoJudicialesService;

    @GetMapping("/view")
    public String view(Model model){

        return "uap/procesoJudiciales/procesoJudicialesView";
    }



}
