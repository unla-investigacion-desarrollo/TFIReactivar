package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Funcion;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long>{
	
	public Funcion findByIdFuncion(Long idFuncion);
	
}
