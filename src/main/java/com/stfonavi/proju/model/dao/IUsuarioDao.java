package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.USR_D_NOMBRES LIKE %:nombre%")
    Page<Usuario> findByUSR_D_NOMBRES(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT u FROM Usuario  u WHERE u.USR_C_ESTADO_CUENTA = '1'")
    List<Usuario> findAllActivos();

    @Query("SELECT u FROM Usuario u WHERE u.USR_D_USUARIO =:nombre")
    Usuario findUsuario(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM SOPORTE.sfsop_usuario_fonavi ORDER BY USR_C_ID DESC",nativeQuery = true)
    List<Usuario> ListarUsuarios();

    @Query(value = "SELECT u FROM Usuario u ORDER BY USR_F_FECHA_CREACION DESC")
    List<Usuario> findAllByOrderByUSR_F_FECHA_CREACION();


    @Query(value="SELECT u FROM Usuario u ORDER BY  u.USR_C_ID DESC")
    Page<Usuario> obtenerTodosUsuarios(Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.USR_D_EMAIL) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(u.USR_D_USUARIO) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Usuario> buscarUsuario(@Param("nombre") String nombre, Pageable pageable);
}
