package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Persona;
import com.unla.alimentar.repositories.PersonaFisicaRepository;
import com.unla.alimentar.vo.PersonaFisicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaFisicaService {

	@Autowired
	private PersonaFisicaRepository repository;

	public Persona traerPersonaFisicaPorId(Long id) {
		return repository.findByIdPersona(id);
	}

	public List<Persona> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarPersonaFisica(long id) {
		Persona registro = repository.findByIdPersona(id);

		if (registro == null) {
			throw new ObjectNotFound("PersonaFisica");
		}

		repository.delete(registro);
	}

	public Persona crearPersonaFisica(PersonaFisicaVo personaFisicaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Persona actualizarPersonaFisica(Long id, PersonaFisicaVo personaFisicaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
