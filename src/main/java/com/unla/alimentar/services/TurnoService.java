package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Turno;
import com.unla.alimentar.repositories.TurnoRepository;

@Service
@Transactional(readOnly = true)
public class TurnoService {

	@Autowired
	private TurnoRepository repository;

	public Turno traerTurnoPorId(Long id) {
		return repository.findByIdTurno(id);
	}

	public List<Turno> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarTurno(long id) {
		Turno registro = repository.findByIdTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("Turno");
		}

		repository.delete(registro);
	}

}
