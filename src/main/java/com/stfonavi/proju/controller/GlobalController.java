package com.stfonavi.proju.controller;

import com.stfonavi.proju.util.helper.Constantes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("version", Constantes.VERSION);
    }

}
