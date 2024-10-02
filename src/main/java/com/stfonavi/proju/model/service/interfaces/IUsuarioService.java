package com.stfonavi.proju.model.service.interfaces;

import com.stfonavi.proju.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> findAll();

    Page<Usuario> findAll(Pageable pageable);

    List<Usuario> findAllByOrderByUSR_F_FECHA_CREACION();

    Page<Usuario> findByNombre(String nombre, Pageable pageable);


    void save(Usuario usuario);

    Usuario findOne(Long USR_C_ID);

    void delete(Long USR_C_ID);

    void cambiarContrasena(Usuario usuario, String nuevaContraseña);

    List<Usuario>  findByUSR_C_ESTADO_CUENTA();

    Usuario findUsuario(String nombre);

    // Página
    Page<Usuario> obtenerTodosUsuarios(Pageable pageable);

    Page<Usuario> buscarUsuario(String nombre,Pageable pageable);
}
