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


    @GetMapping("/view")
    public String mostrarEtapa(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, Constantes.pagSize);
        Page<EtapaProcesal> etapaProcesal = etapaProcesalService.findAll(pageRequest);
        long total = etapaProcesal.getTotalElements();
        PageRender<EtapaProcesal> pageRender = new PageRender<>("/etapaProcesal/view", etapaProcesal);

        model.addAttribute("tituloListado",Constantes.tituloEtapaProcesal);
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("etapaProcesalList",etapaProcesal);
        model.addAttribute("page",pageRender);

        if(etapaProcesal.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }
        return "uap/etapaProcesal/etapaProcesalView";
    }

    @GetMapping(value = "/get")
    public String mostrarFormEtapa(Map<String, Object> model) {
        EtapaProcesal etapaProcesal = new EtapaProcesal();
        model.put("etapaProcesal", etapaProcesal);
        model.put("boton", Constantes.botonNuevo);
        model.put("tituloForm",Constantes.registroForm);

        return "uap/etapaProcesal/formEtapaProcesal";
    }

    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("etapaProcesal") EtapaProcesal etapaProcesal,
                          BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

        // Si hay errores de validación, regresa al formulario
        if (result.hasErrors()) {
            model.addAttribute("etapaProcesal", etapaProcesal);
            model.addAttribute("tituloForm",Constantes.registroForm);
            model.addAttribute("boton",Constantes.botonNuevo);
            return "uap/etapaProcesal/formEtapaProcesal";  // Aquí coloca el nombre de tu plantilla Thymeleaf para el formulario
        }

        etapaProcesalService.save(etapaProcesal);

        String mensajeFlash = (etapaProcesal.getId()  != 0) ? "Edita correctamente": "Creado correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);

        return "redirect:/etapaProcesal/view";
    }


    @GetMapping("/get/{id}")
    public String editar(@PathVariable(value="id")Long id, Map<String,Object>model, RedirectAttributes flash){

        EtapaProcesal etapaProcesal = null;
        String boton;
        if (id != null && id > 0) {  // Aquí cambiamos '||' por '&&' para verificar que el ID sea válido
            etapaProcesal = etapaProcesalService.findOne(id);
            if (etapaProcesal == null) {
                flash.addFlashAttribute("error", "Etapa procesal no existe.");
                return "redirect:/etapaProcesal/view";  // Redirigir a la lista en lugar de mostrar la vista
            }
            boton = Constantes.botonEditar;
        } else {
            flash.addFlashAttribute("error", "ID no válido.");
            return "redirect:/etapaProcesal/view";  // Redirigir si el ID es inválido
        }

        model.put("etapaProcesal",etapaProcesal);
        model.put("tituloForm",Constantes.editarForm);
        model.put("boton",boton);

        return "uap/etapaProcesal/formEtapaProcesal";
    }

}
