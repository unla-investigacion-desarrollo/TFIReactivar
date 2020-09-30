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
import com.unla.reactivar.models.EstadoEmprendimiento;
import com.unla.reactivar.repositories.EstadoEmprendimientoRepository;
import com.unla.reactivar.vo.EstadoEmprendimientoVo;

@Service
@Transactional(readOnly = true)
public class EstadoEmprendimientoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private EstadoEmprendimientoRepository repository;

	public EstadoEmprendimiento traerEstadoEmprendimientoPorId(Long id) {
		log.info("Se traera un estado emprendimiento por id");
		return repository.findByIdEstadoEmprendimiento(id);
	}

	public List<EstadoEmprendimiento> traerTodosEstadosEmprendimiento() {
		log.info("Se traeran todos los estados emprendimiento");
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoEmprendimiento(long id) {
		EstadoEmprendimiento registro = repository.findByIdEstadoEmprendimiento(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoEmprendimiento");
		}

		log.info("Se eliminara el estado emprendimiento [{}]", id);
		repository.delete(registro);
	}

	@Transactional
	public EstadoEmprendimiento actualizarEstadoEmprendimiento(Long id, EstadoEmprendimientoVo estadoEmprendimientoVo) {
		EstadoEmprendimiento estado = repository.findByIdEstadoEmprendimiento(id);

		if (estado == null) {
			throw new ObjectNotFound("EstadoEmprendimiento");
		}

		estado.setEstado(estadoEmprendimientoVo.getNombreEstado());

		try {
			log.info("Se actualizara el estado emprendimiento [{}]", estado.getEstado());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

	@Transactional
	public EstadoEmprendimiento crearEstadoEmprendimiento(EstadoEmprendimientoVo estadoEmprendimientoVo) {
		EstadoEmprendimiento estado = new EstadoEmprendimiento();

		estado.setEstado(estadoEmprendimientoVo.getNombreEstado());

		try {
			log.info("Se creara el estado emprendimiento [{}]", estado.getEstado());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

}
