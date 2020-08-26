package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.PersonaJuridica;

@Repository
public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long>{
	
	public PersonaJuridica findByIdPersona(Long idPersona);

}
