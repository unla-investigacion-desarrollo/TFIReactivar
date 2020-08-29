package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.PersonaFisica;

@Repository
public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long>{
	
	public PersonaFisica findByIdPersona(Long idPersona);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Persona p WHERE p.idPersona = ?1")
	public void deletePersona(Long idPersona);
	
}
