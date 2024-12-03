package com.stfonavi.proju.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {


    @Bean
    public UserDetailsService usersCustom(DataSource dataSource) {

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
/*        users.setUsersByUsernameQuery("SELECT USR_D_USUARIO, USR_D_CONTRASENA, USR_C_ESTADO_CUENTA FROM SOPORTE.SFSOP_USUARIO_FONAVI WHERE USR_D_USUARIO = ? ");

        users.setAuthoritiesByUsernameQuery("SELECT u.USR_D_USUARIO,r.ROL_D_DESCRIPCION FROM SOPORTE.SFSOP_USUARIO_ROL ur "
                + "INNER JOIN SOPORTE.SFSOP_USUARIO_FONAVI u ON u.USR_C_ID= ur.USR_C_ID "
                + "INNER JOIN SOPORTE.SFSOP_ROL r ON r.ROL_C_ID = ur.ROL_C_ID "
                + "WHERE u.USR_D_USUARIO = ? ");
        return users;
    }*/
        users.setUsersByUsernameQuery(
                "SELECT USR_D_USUARIO, USR_D_CLAVE,USR_C_ESTADO_CUENTA " +
                        "FROM SOPORTE.SFSOP_USUARIO_FONAVI " +
                        "WHERE USR_D_USUARIO = ?"
        );

        users.setAuthoritiesByUsernameQuery("SELECT ?, 'ROLE_USER' FROM DUAL");

        return users;
    }

    @Bean
    public AuthenticationManager authenticationManager(DataSource dataSource, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usersCustom(dataSource));
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
//                .disable()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/login",
                        "/logout",
                        "/js/**",
                        "/public/**",
                        "/captcha",
                        "/css/**",
                        "/img/**",
                        "DataTables/**").permitAll()
                .antMatchers("/error", "/error/**").permitAll() // Permitir acceso a las páginas de error
                .anyRequest().authenticated() // Restringe todas las demás rutas a usuarios autenticados
                .and()
                // Habilitar autenticación básica HTTP
//                .httpBasic()
//                .and()
//                .formLogin().loginPage("/login")
//                .permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/403");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}