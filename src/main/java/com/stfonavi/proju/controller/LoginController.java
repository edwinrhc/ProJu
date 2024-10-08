package com.stfonavi.proju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash
    ) {

//        if(principal != null){
//            flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
//            return "redirect:/solicitudes/listar";
//        }

        if(error != null){
            model.addAttribute("error","Error en el login:  Nombre de usuario:  o contraseña incorrecta, por favor vuelva a intentarlo");

        }

        if(logout != null){
            flash.addFlashAttribute("info", "Has cerrado sesion con exito");
            return "redirect:/login";
        }
        return "login"; // Nombre de la plantilla Thymeleaf para el formulario de inicio de sesión
    }
}
