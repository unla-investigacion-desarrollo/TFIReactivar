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

	public EstadoTurno actualizarEstadoTurno(Long id, EstadoTurnoVo estadoTurnoVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public EstadoTurno crearEstadoTurno(EstadoTurnoVo estadoTurnoVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
