package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.FuncionPerfil;

@Repository
public interface FuncionPerfilRepository extends JpaRepository<FuncionPerfil, Long>{
	
	public FuncionPerfil findByIdFuncionPerfil(Long idFuncionPerfil);
	
}
