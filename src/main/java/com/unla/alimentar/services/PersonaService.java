package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Persona;
import com.unla.alimentar.repositories.PersonaRepository;

@Service
@Transactional(readOnly = true)
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	public Persona traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}
	
	public List<Persona> traerTodos(){
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		personaRepository.delete(persona);
	}

}
