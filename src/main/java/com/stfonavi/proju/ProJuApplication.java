package com.stfonavi.proju;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class ProJuApplication extends SpringServletContainerInitializer {

//    @Autowired
//    public BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ProJuApplication.class, args);
    }

}
