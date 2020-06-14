package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	public Usuario findByUsuario(String usuario);
	
}
