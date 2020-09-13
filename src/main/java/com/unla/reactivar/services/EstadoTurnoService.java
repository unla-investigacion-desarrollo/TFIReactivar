package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.EstadoTurno;
import com.unla.reactivar.repositories.EstadoTurnoRepository;
import com.unla.reactivar.vo.EstadoTurnoVo;

@Service
@Transactional(readOnly = true)
public class EstadoTurnoService {

	@Autowired
	private EstadoTurnoRepository repository;

	public EstadoTurno traerEstadoTurnoPorId(Long id) {
		return repository.findByIdEstadoTurno(id);
	}

	public List<EstadoTurno> traerTodosEstadosTurno() {
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

		try {
			estado = repository.save(estado);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}
		
		return estado;
	}

	@Transactional
	public EstadoTurno crearEstadoTurno(EstadoTurnoVo estadoTurnoVo) {
		EstadoTurno estado = new EstadoTurno();
		
		estado.setEstado(estadoTurnoVo.getNombreEstado());

		try {
			estado = repository.save(estado);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}
		
		return estado;
	}

}
