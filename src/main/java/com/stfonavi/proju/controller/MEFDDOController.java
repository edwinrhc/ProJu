package com.stfonavi.proju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mefddo")
public class MEFDDOController {


    @GetMapping("/mefDdoView")
    public String mostrarMefDDO(Model model){

        model.addAttribute("titulo","Reporte MEF.DDO");

        return "uap/mefddo/mefDdoView";
    }

}
