package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import com.stfonavi.proju.model.service.interfaces.IProcesoJudicialesService;
import com.stfonavi.proju.util.helper.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/procesoJudiciales")
public class ProcesoJudicialesController {

    @Autowired
    private IProcesoJudicialesService procesoJudicialesService;

    @GetMapping("/view")
    public String view(Model model){

        return "uap/procesoJudiciales/procesoJudicialesView";
    }


    @GetMapping("/get")
    public String mostrarForm(Map<String,Object> model){

        ProcesoJudiciales procesoJudiciales = new ProcesoJudiciales();
        model.put("procesoJudiciales",procesoJudiciales);
        model.put("boton", Constantes.botonNuevo);
        model.put("titulo",Constantes.registroForm);

        return "uap/procesoJudiciales/formProcesoJudiciales";
    }


    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("procesoJudiciales") ProcesoJudiciales procesoJudiciales, BindingResult result,
                          Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("procesoJudiciales",procesoJudiciales);
            model.addAttribute("titulo",Constantes.registroForm);
            model.addAttribute("boton",Constantes.botonNuevo);
            return "uap/procesoJudiciales/formProcesoJudiciales";
        }
        procesoJudicialesService.save(procesoJudiciales);
        String mensajeFlash = (procesoJudiciales.getIdProcesoJudicial() != 0) ? "Editar Correctamente": "Creado Correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);

        return "redirect:/procesoJudiciales/view";
    }


}
