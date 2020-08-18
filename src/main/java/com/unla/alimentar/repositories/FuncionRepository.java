package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Funcion;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long>{
	
	public Funcion findByIdFuncion(Long idFuncion);
	
}
