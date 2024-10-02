package com.stfonavi.proju.model.dao;

import com.stfonavi.proju.model.entity.Rol;
import com.stfonavi.proju.model.entity.Usuario;
import com.stfonavi.proju.model.entity.UsuarioRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUsuarioRolDao extends JpaRepository<UsuarioRol, Integer> {

    @Query("SELECT ur FROM UsuarioRol ur")
    Page<UsuarioRol> findByUsrolCID(Pageable pageable);

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usrCID = :nombre")
    Page<UsuarioRol> findByUsrCID(@Param("nombre") String nombre, Pageable pageable);

    boolean existsByUsuarioAndRol(Usuario usuario, Rol rol);


    void deleteByUsrCID(Long usrCId);

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usrCID = :usrCId")
    List<UsuarioRol> findByUsrCID(@Param("usrCId")Long usrCId);


    @Transactional
    @Modifying
    @Query("UPDATE UsuarioRol ur SET ur.usrolEstado = :estado WHERE ur.usrolCID = :usrolCID")
    void updateByUsrolEstado(@Param("estado") Integer estado, @Param("usrolCID") Integer usrolCID);
}
