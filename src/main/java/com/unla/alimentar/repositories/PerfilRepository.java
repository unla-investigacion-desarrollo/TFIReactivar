package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	public Perfil findByIdPerfil(Long id);
	
}
