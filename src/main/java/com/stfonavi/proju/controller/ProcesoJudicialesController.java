package com.stfonavi.proju.controller;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.dto.ProcesoJudicialesDTO;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.entity.Juzgado;
import com.stfonavi.proju.model.entity.ProcesoJudiciales;
import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.*;
import com.stfonavi.proju.util.helper.Constantes;
import com.stfonavi.proju.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("procesoJudiciales")
@RequestMapping("/procesoJudiciales")
public class ProcesoJudicialesController {

    private static final Logger logger = LoggerFactory.getLogger(ProcesoJudicialesController.class);

    @Autowired
    private IProcesoJudicialesService procesoJudicialesService;

    @Autowired
    private IJuzgadoService juzgadoService;

    @Autowired
    private IEtapaProcesalService etapaProcesalService;

    @Autowired
    private ITipoContigenciaService tipoContigenciaService;

    @Autowired
    private IMovimientoService movimientoService;

    @GetMapping("/view")
    public String view(@RequestParam(name="page",defaultValue = "0")int page, Model model){
        //Obtener todos los juzgados
        List<Juzgado> Listjuzgados = juzgadoService.ListarTodos();
//        ProcesoJudiciales procesoJudiciales = new ProcesoJudiciales();
//        procesoJudiciales.setMovimientos(movimientoService.obtenerMovimientos(procesoJudiciales.getIdProcesoJudicial()));

        //Configurar la paginación para el listado
        Pageable pageRequest = PageRequest.of(page, Constantes.pagSize);
        Page<ProcesoJudiciales> procesoJud = procesoJudicialesService.findAll(pageRequest);
        logger.info("Cuantos son: "+ procesoJud.toString());
        long total = procesoJud.getTotalElements();
        PageRender<ProcesoJudiciales> pageRender = new PageRender<>("/procesoJudiciales/view",procesoJud);

        model.addAttribute("titulo",Constantes.registroForm);
        model.addAttribute("dateFormat", Constantes.getSimpleDateFormat());
        model.addAttribute("total",total);
        model.addAttribute("procesoJudicialesList",procesoJud);
        model.addAttribute("page",pageRender);
//        model.addAttribute("procesoJudiciales",procesoJudiciales);
        model.addAttribute("Listjuzgados",Listjuzgados);
        model.addAttribute("boton", Constantes.botonNuevo);

        if(procesoJud.isEmpty()){
            model.addAttribute("mensaje","No se encontraron resultados");
        }

        return "uap/procesoJudiciales/procesoJudicialesView";
    }


//    @GetMapping("/get")
//    public String mostrarForm(@RequestParam(value="id", required = false) Long id, Map<String,Object> model){
//        List<Juzgado> Listjuzgados = juzgadoService.ListarTodos();
//        ProcesoJudiciales procesoJudiciales = new ProcesoJudiciales();
//        // Verificamos
//        if(id == null){
//            List<MovimientoDetailDTO> movimientoDetails = movimientoService.getMovimientoDetailsByProcesoJudicialId(id);
//            model.put("mensaje", "Agrega movimientos para el proceso judicial");
//        }
////        procesoJudiciales.setMovimientos(movimientoService.obtenerMovimientos(procesoJudiciales.getIdProcesoJudicial()));
//        model.put("procesoJudiciales",procesoJudiciales);
//        model.put("Listjuzgados",Listjuzgados);
//        model.put("boton", Constantes.botonNuevo);
//        model.put("titulo",Constantes.registroForm);
//        return "uap/procesoJudiciales/formProcesoJudiciales";
//    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getMostrarForm(){
        ProcesoJudicialesDTO dto = new ProcesoJudicialesDTO();
        List<EtapaProcesal> etapaProcesales = etapaProcesalService.ListarTodos();
        List<TipoContigencia> tipoContigencia = tipoContigenciaService.findAll();
        List<Juzgado> juzgado = juzgadoService.ListarTodos();

        Map<String,Object> response = new HashMap<>();
        response.put("procesoJudiciales",dto);
        response.put("juzgado",juzgado);
        response.put("etapaProcesales",etapaProcesales);
        response.put("tipoContigencia",tipoContigencia);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProcesoJudicialesDTO> getFindOne(@PathVariable(value = "id") Long id) {
        ProcesoJudiciales procesoJudiciales = procesoJudicialesService.findOne(id);
        if(procesoJudiciales == null){
            return ResponseEntity.notFound().build();
        }

        ProcesoJudicialesDTO dto = new ProcesoJudicialesDTO();
        dto.setIdProcesoJudicial(procesoJudiciales.getIdProcesoJudicial());
        dto.setNumExpediente(procesoJudiciales.getNumExpediente());
        dto.setMateria(procesoJudiciales.getMateria());
        dto.setMonto(procesoJudiciales.getMonto());
        dto.setTipoMoneda(procesoJudiciales.getTipoMoneda());
        dto.setDemandante(procesoJudiciales.getDemandante());
        dto.setDemandado(procesoJudiciales.getDemandado());
        dto.setIdJuzgado(procesoJudiciales.getIdJuzgado());
        dto.setAbogado(procesoJudiciales.getAbogado());

        dto.setIdEtapaProcesal(procesoJudiciales.getIdEtapaProcesal());
        dto.setNombreEtapaProcesal(procesoJudiciales.getEtapaProcesal().getNombre());
        dto.setNombreContingencia(procesoJudiciales.getTipoContigencia().getNombre());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/crear")
    public String guardar(@Valid @ModelAttribute("procesoJudiciales") ProcesoJudiciales procesoJudiciales, BindingResult result,
                          Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            List<Juzgado> Listjuzgados = juzgadoService.ListarTodos();
            model.addAttribute("procesoJudiciales",procesoJudiciales);
//            model.addAttribute("Listjuzgados",Listjuzgados);
            model.addAttribute("titulo",Constantes.registroForm);
            model.addAttribute("boton",Constantes.botonNuevo);

            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            return "uap/procesoJudiciales/formProcesoJudiciales";
        }

        procesoJudicialesService.save(procesoJudiciales);

        String mensajeFlash = (procesoJudiciales.getIdProcesoJudicial() != 0) ? "Editar Correctamente": "Creado Correctamente";
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);

        return "redirect:/procesoJudiciales/view";
    }




    @PostMapping("/create")
    public ResponseEntity<?> crearProcesoJudicial(@RequestBody ProcesoJudicialesDTO procesoJudicialesDTO){
        try{
            procesoJudicialesService.guardarProcesoJudicial(procesoJudicialesDTO);
            return ResponseEntity.ok("Proceso Judicial creado");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()+ "Error al crear el proceso judicial");
        }

    }

    @GetMapping("/details/{id}")
    public String editar(@PathVariable(value = "id")Long id, Map<String,Object> model, RedirectAttributes flash){

        ProcesoJudiciales procesoJudiciales = null;
        String boton;
        if(id != null && id > 0){

            List<Juzgado> Listjuzgados = juzgadoService.ListarTodos();
            procesoJudiciales = procesoJudicialesService.findOne(id);
            if(procesoJudiciales == null){
                flash.addFlashAttribute("error","Proceso Judiciales no existe");
                return "redirect:/procesoJudiciales/view";
            }


            boton = Constantes.botonEditar;

            List<MovimientoDetailDTO> movimientoDetails = movimientoService.getMovimientoDetailsByProcesoJudicialId(id);

            if (movimientoDetails.isEmpty()) {
                model.put("mensaje", "No se encontraron movimientos para el proceso judicial");
            }
            model.put("movimientoDetails", movimientoDetails);
            model.put("Listjuzgados",Listjuzgados);

        }else{
            flash.addFlashAttribute("error","Id no válido");
            return "redirect:/procesoJudiciales/view";
        }

        model.put("procesoJudiciales",procesoJudiciales);
        model.put("titulo",Constantes.editarForm);
        model.put("boton",boton);
        return "uap/procesoJudiciales/formProcesoJudiciales";
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateProcesoJudicial(@RequestBody ProcesoJudicialesDTO procesoJudicialesDTO){
        try{

            ProcesoJudiciales procesoJudiciales = procesoJudicialesService.findOne(procesoJudicialesDTO.getIdProcesoJudicial());
            if(procesoJudiciales == null){
                return ResponseEntity.notFound().build();
            }

            procesoJudiciales.setIdProcesoJudicial(procesoJudicialesDTO.getIdProcesoJudicial());
            procesoJudiciales.setNumExpediente(procesoJudicialesDTO.getNumExpediente());
            procesoJudiciales.setMateria(procesoJudicialesDTO.getMateria());
            procesoJudiciales.setTipoMoneda(procesoJudicialesDTO.getTipoMoneda());
            procesoJudiciales.setMonto(procesoJudicialesDTO.getMonto());
            procesoJudiciales.setDemandante(procesoJudicialesDTO.getDemandante());
            procesoJudiciales.setDemandado(procesoJudicialesDTO.getDemandado());
            procesoJudiciales.setAbogado(procesoJudicialesDTO.getAbogado());
            procesoJudiciales.setIdJuzgado(procesoJudicialesDTO.getIdJuzgado());


            procesoJudiciales.setIdEtapaProcesal(procesoJudicialesDTO.getIdEtapaProcesal());
            procesoJudiciales.setIdContigencia(procesoJudicialesDTO.getIdContingencia());

            procesoJudicialesService.updateProcesoJudicial(procesoJudicialesDTO);

            return ResponseEntity.ok("Proceso judicial actualizado");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()+ "Error al actualizar el proceso de judiciales");
        }
    }


    @GetMapping("/get/options")
    public ResponseEntity<Map<String,Object>> getOptions(){
        Map<String,Object> response = new HashMap<>();
        List<Juzgado> juzgadosList = juzgadoService.ListarTodos();
        List<EtapaProcesal> etapaProcesals = etapaProcesalService.ListarTodos();
        List<TipoContigencia> tipoContigencias = tipoContigenciaService.findAll();
        response.put("juzgadosList",juzgadosList);
        response.put("etapaProcesals",etapaProcesals);
        response.put("tipoContigencias",tipoContigencias);
        return ResponseEntity.ok(response);
    }

}
