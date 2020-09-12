package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.PersonaFisica;

@Repository
public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long>{
		
	@Query("SELECT p FROM Persona p WHERE p.idPersona = ?1 and tipo_persona = 'fisica'")
	public PersonaFisica findByIdPersona(Long idPersona);
	
	@Modifying
	@Query("SELECT p FROM Persona p WHERE tipo_persona = 'fisica'")
	public List<PersonaFisica> findAll();
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Persona p WHERE p.idPersona = ?1")
	public void deletePersona(Long idPersona);
	
}