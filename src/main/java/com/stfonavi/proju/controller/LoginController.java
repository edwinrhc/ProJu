package com.stfonavi.proju.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping({"/", "/login"})
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginWithCaptcha(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("captcha") String captcha,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        String sessionCaptcha = (String) session.getAttribute("captcha");
        session.removeAttribute("captcha"); // Eliminar el captcha después de usarlo

        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            redirectAttributes.addFlashAttribute("error", "Captcha incorrecto. Intente nuevamente.");
            return "redirect:/login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }
    
}
