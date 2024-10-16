package com.stfonavi.proju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mefddo")
public class MEFDDOController {

//TODO: ELIMINAR EL CONTROLLER Y SUS VISTAS
    @GetMapping("/mefDdoView")
    public String mostrarMefDDO(Model model){

        model.addAttribute("titulo","Reporte MEF.DDO");

        return "uap/mefddo/mefDdoView";
    }

    @GetMapping("/mefDdoViewMovimiento")
    public String mostrarMefDDOMovimiento(Model model){

//        model.addAttribute("titulo","Reporte MEF.DDO");

        return "uap/mefddo/mefDdoMovimientoView";
    }

}
