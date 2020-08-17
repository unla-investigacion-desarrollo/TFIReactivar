package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.modelo.Persona;
import com.unla.alimentar.repository.PersonaRepository;

@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository personaRepository;

	public Persona traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}

}
