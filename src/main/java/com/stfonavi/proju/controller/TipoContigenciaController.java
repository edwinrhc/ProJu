package com.stfonavi.proju.controller;

import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.ITipoContigenciaService;
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
@SessionAttributes("tipoContigencia")
@RequestMapping("/tipoContigencia")
public class TipoContigenciaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoContigenciaController.class);

    @Autowired
    private ITipoContigenciaService tipoContigenciaService;


    @GetMapping("/view")
    public String mostrar(@RequestParam(name ="page",defaultValue = "0")int page, Model model){

        Pageable pageRequest = PageRequest.of(page, Constantes.pagSize);
        Page<TipoContigencia> tipoContigencias = tipoContigenciaService.findAll(pageRequest);
        logger.info(tipoContigencias.toString());

        long total = tipoContigencias.getTotalElements();
        PageRender<TipoContigencia> pageRender = new PageRender<>("/tipoContigencia/view",tipoContigencias);

        model.addAttribute("titulo","Tipo Contigencia");
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("tipoContigenciaList",tipoContigencias);
        model.addAttribute("page",pageRender);

        if(tipoContigencias.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }


        return  "uap/tipoContigencia/tipoContigenciaView";
    }

    @GetMapping(value = "/get")
    public String mostrarFormEtapa(Map<String, Object> model) {
        TipoContigencia tipoContigencia = new TipoContigencia();
        model.put("tipoContigencia", tipoContigencia);
        model.put("boton", "Registro nuevo");
        model.put("titulo", "Registre un nuevo tipo de contigencia");

        return "uap/tipoContigencia/formTipoContigencia";
    }

    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("tipoContigencia") TipoContigencia tipoContigencia, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        // si encontramos errores de validació
        if(result.hasErrors()){
            model.addAttribute("tipoContigencia",tipoContigencia);
            model.addAttribute("titulo", "Registre un nuevo tipo de contigencia");
            model.addAttribute("boton", "Registro nuevo");
            return "uap/tipoContigencia/formTipoContigencia";
        }
        tipoContigenciaService.save(tipoContigencia);

        String mensajeFlash = (tipoContigencia.getIdTipoContigencia() != 0) ? "Editar Correctamente": "Creado Correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);

        return "redirect:/tipoContigencia/view";
    }

    @GetMapping("/get/{id}")
    public String editar(@PathVariable(value="id")Long id, Map<String,Object>model, RedirectAttributes flash){

        TipoContigencia tipoContigencia = null;
        String boton;
        if(id != null && id > 0){
            tipoContigencia = tipoContigenciaService.findOne(id);
            if(tipoContigencia == null){
                flash.addFlashAttribute("error","Tipo Contigencia procesal no existe");
                return "redirect:/tipoContigencia/view";
            }
            boton = "Editar Registro";
        }else {
            flash.addFlashAttribute("error", "ID no válido");
            return "redirect:/tipoContigencia/view";
        }

        model.put("tipoContigencia",tipoContigencia);
        model.put("titulo", "Actualice un tipo de contigencia");

        model.put("boton", boton);


        return "uap/tipoContigencia/formTipoContigencia";
    }




}
