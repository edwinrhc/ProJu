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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("etapaProcesal")
@RequestMapping("/etapaProcesal")
public class EtapaProcesalController {

    @Autowired
    private IEtapaProcesalService etapaProcesalService;


    @GetMapping("/etapaProcesalView")
    public String mostrarEtapa(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<EtapaProcesal> etapaProcesal = etapaProcesalService.findAll(pageRequest);
        long total = etapaProcesal.getTotalElements();
        PageRender<EtapaProcesal> pageRender = new PageRender<>("/etapaProcesal/etapaProcesalView", etapaProcesal);

        model.addAttribute("titulo","Listado de etapas procesales");
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("etapaProcesalList",etapaProcesal);
        model.addAttribute("page",pageRender);

        if(etapaProcesal.isEmpty()){
            model.addAttribute("mensaje","No se encontraron usuarios");
        }

        return "uap/etapaProcesal/etapaProcesalView";
    }

    @GetMapping(value = "/get")
    public String mostrarFormEtapa(Map<String, Object> model) {
        EtapaProcesal etapaProcesal = new EtapaProcesal();
        model.put("etapaProcesal", etapaProcesal);
        model.put("boton", "Registro nuevo");

        return "uap/etapaProcesal/formEtapaProcesal";
    }

    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("etapaProcesal") EtapaProcesal etapaProcesal,
                          BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

        // Si hay errores de validación, regresa al formulario
        if (result.hasErrors()) {
            model.addAttribute("etapaProcesal", etapaProcesal);
            return "uap/etapaProcesal/formEtapaProcesal";  // Aquí coloca el nombre de tu plantilla Thymeleaf para el formulario
        }

        etapaProcesalService.save(etapaProcesal);

        String mensajeFlash = (etapaProcesal.getId()  != 0) ? "Edita correctamente": "Creado correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);

        return "redirect:/etapaProcesal/etapaProcesalView";
    }


    @GetMapping("/get/{id}")
    public String editar(@PathVariable(value="id")Long id, Map<String,Object>model, RedirectAttributes flash){

        EtapaProcesal etapaProcesal = null;
        String boton;
        if (id != null && id > 0) {  // Aquí cambiamos '||' por '&&' para verificar que el ID sea válido
            etapaProcesal = etapaProcesalService.findOne(id);
            if (etapaProcesal == null) {
                flash.addFlashAttribute("error", "Etapa procesal no existe.");
                return "redirect:/etapaProcesal/etapaProcesalView";  // Redirigir a la lista en lugar de mostrar la vista
            }
            boton = "Editar Registro";
        } else {
            flash.addFlashAttribute("error", "ID no válido.");
            return "redirect:/etapaProcesal/etapaProcesalView";  // Redirigir si el ID es inválido
        }


        model.put("etapaProcesal",etapaProcesal);
        model.put("titulo","Editar");
        model.put("boton",boton);

        return "uap/etapaProcesal/formEtapaProcesal";
    }

}
