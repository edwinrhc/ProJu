package com.stfonavi.proju.controller;


import com.stfonavi.proju.model.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IUsuarioService usuarioService;



    @GetMapping("/")
    public String home(Model model, Principal principal, Authentication authentication){
        String usuarioLogeado = ((UserDetails) authentication.getPrincipal()).getUsername();
        String username = principal.getName();
        model.addAttribute("username",username);
        return "home";
    }



}