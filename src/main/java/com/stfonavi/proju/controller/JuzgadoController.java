package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.entity.Juzgado;
import com.stfonavi.proju.model.service.interfaces.IJuzgadoService;
import com.stfonavi.proju.util.helper.Constantes;
import com.stfonavi.proju.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

@Controller
@SessionAttributes("juzgado")
@RequestMapping("/juzgado")
public class JuzgadoController {
    private static final Logger logger = LoggerFactory.getLogger(JuzgadoController.class);
    @Autowired
    private IJuzgadoService juzgadoService;

    @GetMapping("/view")
    public String mostrar(@RequestParam(name="page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page, Constantes.pagSize);
        Page<Juzgado> juzgado = juzgadoService.findAll(pageRequest);
        logger.info(juzgado.toString());
        long total = juzgado.getTotalElements();
        PageRender<Juzgado> pageRender = new PageRender<>("/juzgado/view",juzgado);
        model.addAttribute("titulo",Constantes.tituloJuzgado);
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("juzgadoList", juzgado);
        model.addAttribute("page",pageRender);

        if(juzgado.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }

        return "uap/juzgado/juzgadoView";
    }


    @GetMapping(value = "/get")
    public String mostrarForm(Map<String,Object> model){

        Juzgado juzgado = new Juzgado();
        model.put("juzgado",juzgado);
        model.put("boton",Constantes.botonNuevo);
        model.put("titulo",Constantes.registroForm);

        return "uap/juzgado/formJuzgado";
    }

    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("juzgado") Juzgado juzgado, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("juzgado", juzgado);
            model.addAttribute("titulo",Constantes.registroForm);
            model.addAttribute("boton",Constantes.botonNuevo);
            return "uap/juzgado/formJuzgado";
        }
        juzgadoService.save(juzgado);

        String mensajeFlash = (juzgado.getIdJuzgado() != 0) ? "Editar Correctamente": "Creado Correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);
        return "redirect:/juzgado/view";
    }

    @GetMapping("/get/{id}")
    public String editar(@PathVariable(value="id")Long id, Map<String,Object> model, RedirectAttributes flash){

        Juzgado juzgado = null;
        String boton;
        if(id != null && id > 0){
            juzgado = juzgadoService.findOne(id);
            if(juzgado == null){
                flash.addFlashAttribute("error","Juzgado no existe");
                return "redirect:/juzgado/view";
            }
            boton = Constantes.botonEditar;
        }else{
            flash.addFlashAttribute("error","Id no válido");
            return "redirect:/juzgado/view";
        }
        model.put("juzgado",juzgado);
        model.put("titulo",Constantes.editarForm);
        model.put("boton",boton);

        return "uap/juzgado/formJuzgado";
    }


    @GetMapping("/search")
    public String buscarJuzgado(@RequestParam(value="nombre", required = false) String nombre,
                                @RequestParam(name="page", defaultValue = "0")int page,
                                Model model){

        Pageable pageable = PageRequest.of(page,  Constantes.pagSize);
        Page<Juzgado> juzgado = juzgadoService.buscarJuzgado(nombre,pageable);
        PageRender<Juzgado> pageRender = new PageRender<>("/juzgado/search",juzgado);
        long total = juzgado.getTotalElements();
        model.addAttribute("titulo",Constantes.tituloJuzgado);
        model.addAttribute("juzgadoList",juzgado);

        if(juzgado.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }
        model.addAttribute("nombreBusqueda",nombre);
        model.addAttribute("titulo","Listado de Juzgados");
        model.addAttribute("juzgadoList",juzgado);
        model.addAttribute("dateFormat",Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("page",pageRender);

        return "uap/juzgado/juzgadoView";
    }

}
