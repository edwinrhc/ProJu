package com.stfonavi.proju.model.service.implementation;

import com.stfonavi.proju.model.dao.IUsuarioDao;
import com.stfonavi.proju.model.entity.Usuario;
import com.stfonavi.proju.model.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl  implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;


    @Autowired
    private PasswordEncoder passwordEncoder; // Suponiendo que utilizas un encoder para las contraseñas


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllByOrderByUSR_F_FECHA_CREACION() {
        return usuarioDao.findAllByOrderByUSR_F_FECHA_CREACION();
    }

    @Override
    public Page<Usuario> findByNombre(String nombre, Pageable pageable) {
        return usuarioDao.findByUSR_D_NOMBRES(nombre, pageable);
    }


    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findOne(Long USR_C_ID) {
        return usuarioDao.findById(USR_C_ID).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long USR_C_ID) {
        usuarioDao.deleteById(USR_C_ID);
    }

    @Override
    public void cambiarContrasena(Usuario usuario, String nuevaContrasena) {
        // Encriptar la nueva contraseña usando el passwordEncoder
        String contrasenaEncriptada = passwordEncoder.encode(nuevaContrasena);
        // Actualizar la contraseña del usuario en la base de datos
        usuario.setUSR_D_CONTRASENA(contrasenaEncriptada);
        usuarioDao.save(usuario);
    }

    @Override
    public List<Usuario> findByUSR_C_ESTADO_CUENTA() {
        return usuarioDao.findAllActivos();
    }

    @Override
    public Usuario findUsuario(String nombre) {
        return usuarioDao.findUsuario(nombre);
    }

    @Override
    public Page<Usuario> obtenerTodosUsuarios(Pageable pageable) {
        return usuarioDao.obtenerTodosUsuarios(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> buscarUsuario(String nombre, Pageable pageable) {
        return usuarioDao.buscarUsuario(nombre,pageable);
    }
}
