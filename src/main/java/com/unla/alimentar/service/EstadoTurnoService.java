package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.EstadoTurno;
import com.unla.alimentar.repository.EstadoTurnoRepository;

@Service
@Transactional(readOnly = true)
public class EstadoTurnoService {

	@Autowired
	private EstadoTurnoRepository repository;

	public EstadoTurno traerEstadoTurnoPorId(Long id) {
		return repository.findByIdEstadoTurno(id);
	}

	public List<EstadoTurno> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoTurno(long id) {
		EstadoTurno registro = repository.findByIdEstadoTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoTurno");
		}

		repository.delete(registro);
	}

}
