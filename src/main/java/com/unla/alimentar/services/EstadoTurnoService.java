package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.EstadoTurno;
import com.unla.alimentar.repositories.EstadoTurnoRepository;
import com.unla.alimentar.vo.EstadoTurnoVo;

@Service
@Transactional(readOnly = true)
public class EstadoTurnoService {

	@Autowired
	private EstadoTurnoRepository repository;

	public EstadoTurno traerEstadoTurnoPorId(Long id) {
		return repository.findByIdEstadoTurno(id);
	}

	public List<EstadoTurno> traerTodos() {
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

	@Transactional
	public EstadoTurno actualizarEstadoTurno(Long id, EstadoTurnoVo estadoTurnoVo) {
		EstadoTurno estado = repository.findByIdEstadoTurno(id);

		if (estado == null) {
			throw new ObjectNotFound("EstadoTurno");
		}

		estado.setEstado(estadoTurnoVo.getNombreEstado());

		return repository.save(estado);
	}

	@Transactional
	public EstadoTurno crearEstadoTurno(EstadoTurnoVo estadoTurnoVo) {
		EstadoTurno estado = new EstadoTurno();
		
		estado.setEstado(estadoTurnoVo.getNombreEstado());

		return repository.save(estado);
	}

}
