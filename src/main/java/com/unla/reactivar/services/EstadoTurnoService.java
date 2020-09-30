package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private EstadoTurnoRepository repository;

	public EstadoTurno traerEstadoTurnoPorId(Long id) {
		log.info("Se traera un estado turno por id");
		return repository.findByIdEstadoTurno(id);
	}

	public List<EstadoTurno> traerTodosEstadosTurno() {
		log.info("Se traeran todos los estados turnos");
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoTurno(long id) {
		EstadoTurno registro = repository.findByIdEstadoTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoTurno");
		}

		log.info("Se eliminara el estado turno [{}]", id);
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
			log.info("Se actualizara el estado turno [{}]", estado.getEstado());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

	@Transactional
	public EstadoTurno crearEstadoTurno(EstadoTurnoVo estadoTurnoVo) {
		EstadoTurno estado = new EstadoTurno();

		estado.setEstado(estadoTurnoVo.getNombreEstado());

		try {
			log.info("Se creara el estado turno [{}]", estado.getEstado());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

}
