package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	public Perfil findByIdPerfil(Long id);
	
}
