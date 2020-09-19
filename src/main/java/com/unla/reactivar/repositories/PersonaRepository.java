package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	@Query("SELECT p FROM Persona p WHERE p.idPersona = ?1")
	public Persona findByIdPersona(Long idPersona);

	@Modifying
	@Query("SELECT p FROM Persona p")
	public List<Persona> findAll();

	@Transactional
	@Modifying
	@Query("DELETE FROM Persona p WHERE p.idPersona = ?1")
	public void deletePersona(Long idPersona);
	
	@Query("SELECT p FROM Persona p JOIN Login l ON l.idLogin = p.login WHERE l.email = ?1")
	public Persona findByEmail(String email);
}
