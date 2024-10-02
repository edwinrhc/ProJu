package com.stfonavi.proju;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProJuApplication implements CommandLineRunner {

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ProJuApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        String password = "123456";

        String password2 = "345678";

        for(int i=0; i<2;i++){
            String bcryptPassword = passwordEncoder.encode(password);
            //    System.out.println("Contraseña de 1 al 6: "+ bcryptPassword);
        }

        for(int i=0; i<2;i++){
            String bcryptPassword2 = passwordEncoder.encode(password2);
            //  System.out.println("Contraseña de 3 al 7: "+ bcryptPassword2);
        }
    }
}
