package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaRepository;
import com.unla.reactivar.vo.CoordenadasVo;

@Service
@Transactional(readOnly = true)
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	public Persona traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}
	
	public Persona traerPersonaPorEmail(String email) {
		return personaRepository.findByEmail(email);
	}

	public List<Persona> traerTodos() {
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		personaRepository.deletePersona(id);
	}

	public CoordenadasVo traerCoordenadas(Long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		Ubicacion ubicacion = persona.getUbicacion();

		return new CoordenadasVo(ubicacion.getLatitud(), ubicacion.getLongitud());
	}



}
