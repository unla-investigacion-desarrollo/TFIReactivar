package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.PersonaJuridica;

@Repository
public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long> {

	@Query("SELECT p FROM Persona p WHERE p.idPersona = ?1 and tipo_persona = 'juridica'")
	public PersonaJuridica findByIdPersona(Long idPersona);

	@Modifying
	@Query("SELECT p FROM Persona p WHERE tipo_persona = 'juridica'")
	public List<PersonaJuridica> findAll();

	@Transactional
	@Modifying
	@Query("DELETE FROM Persona p WHERE p.idPersona = ?1")
	public void deletePersona(Long idPersona);

	@Query("SELECT p FROM Persona p WHERE p.estadoPersona = 1")
	public List<PersonaJuridica> findAllInactivos();

	@Query("SELECT p FROM Persona p WHERE tipo_persona = 'juridica' and p.estadoPersona = (Select e from EstadoPersona e  where e.idEstadoPersona=:estadoPersona)")
	public List<PersonaJuridica> findAllPersonasByEstado(@Param("estadoPersona") Long estadoPersona);

}
