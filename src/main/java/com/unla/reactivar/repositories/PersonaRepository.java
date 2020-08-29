package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{
	
	public Persona findByIdPersona(Long idPersona);

	@Transactional
	@Modifying
	@Query("DELETE FROM Persona p WHERE p.idPersona = ?1")
	public void deletePersona(Long idPersona);
}
