package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.EstadoPersona;
import com.unla.reactivar.repositories.EstadoPersonaRepository;
import com.unla.reactivar.vo.EstadoPersonaVo;

@Service
@Transactional(readOnly = true)
public class EstadoPersonaService {

	@Autowired
	private EstadoPersonaRepository repository;

	public EstadoPersona traerEstadoPersonaPorId(Long id) {
		return repository.findByIdEstadoPersona(id);
	}

	public List<EstadoPersona> traerTodosEstadosPersona() {
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoPersona(long id) {
		EstadoPersona registro = repository.findByIdEstadoPersona(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoPersona");
		}

		repository.delete(registro);
	}

	@Transactional
	public EstadoPersona actualizarEstadoPersona(Long id, EstadoPersonaVo estadoPersonaVo) {
		EstadoPersona estado = repository.findByIdEstadoPersona(id);

		if (estado == null) {
			throw new ObjectNotFound("EstadoPersona");
		}

		estado.setEstado(estadoPersonaVo.getNombreEstado());

		try {
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

	@Transactional
	public EstadoPersona crearEstadoPersona(EstadoPersonaVo estadoPersonaVo) {
		EstadoPersona estado = new EstadoPersona();

		estado.setEstado(estadoPersonaVo.getNombreEstado());

		try {
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

}
