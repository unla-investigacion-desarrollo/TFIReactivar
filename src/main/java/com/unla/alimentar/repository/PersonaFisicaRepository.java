package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Persona;

@Repository
public interface PersonaFisicaRepository extends JpaRepository<Persona, Long>{
	
	public Persona findByIdPersona(Long idPersona);
	
}
