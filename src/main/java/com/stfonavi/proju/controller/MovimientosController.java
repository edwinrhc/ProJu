package com.stfonavi.proju.controller;

import com.stfonavi.proju.dto.MovimientoDetailDTO;
import com.stfonavi.proju.model.entity.EtapaProcesal;
import com.stfonavi.proju.model.entity.Movimiento;
import com.stfonavi.proju.model.entity.TipoContigencia;
import com.stfonavi.proju.model.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@SessionAttributes("movimientos")
@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    private static final Logger logger = LoggerFactory.getLogger(MovimientosController.class);

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


    //    @GetMapping("/get/{id}")
//    public String editar(@PathVariable(value = "id")Long id, Map<String,Object> model, RedirectAttributes flash){
//
//        Movimiento movimiento = movimientoService.findOne(id);
//
//        model.put("movimiento",movimiento);
//
//        return  "uap/movimiento/modalMovimiento";
//    }
    @GetMapping("/get/{id}")
    public ResponseEntity<MovimientoDetailDTO> getMovimiento(@PathVariable("id") Long id) {
        Movimiento movimiento = movimientoService.findOne(id);

        if (movimiento == null) {
            // Retorna un estado 404 Not Found si el movimiento no existe
            return ResponseEntity.notFound().build();
        }

        // Convierte la entidad Movimiento a MovimientoDTO
        MovimientoDetailDTO dto = new MovimientoDetailDTO();

        dto.setIdMovimiento(movimiento.getIdMovimiento());
        dto.setNombre(movimiento.getNombre());
        dto.setIdEtapaProcesal(movimiento.getIdEtapaProcesal());
//        dto.setIdProcesoJudicial(movimiento.getIdProcesoJudicial());

        return ResponseEntity.ok(dto);
    }




    @GetMapping("/get")
    public ResponseEntity<Map<String,Object>> getMostrarForm(){
        MovimientoDetailDTO dto = new MovimientoDetailDTO(); // Objeto vacío para datos nuevos
        List<EtapaProcesal> etapaProcesales = etapaProcesalService.ListarTodos();
        List<TipoContigencia> tipoContigencia = tipoContigenciaService.findAll();

        // Preparamos el map
        Map<String, Object>reponse = new HashMap<>();
        reponse.put("movimiento",dto);
        reponse.put("etapaProcesales",etapaProcesales);
        reponse.put("tipoContigencia",tipoContigencia);

        return ResponseEntity.ok(reponse);
    }
    @PostMapping("/create")
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDetailDTO movimientoDetailDTO){
        System.out.println("Movimientos ->" + movimientoDetailDTO);
        try{
            movimientoService.guardarMovimiento(movimientoDetailDTO);
            return ResponseEntity.ok("Movimiento creado");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + " Error al crear el movimiento");
        }
    }





}
