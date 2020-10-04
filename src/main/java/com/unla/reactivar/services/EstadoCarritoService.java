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
import com.unla.reactivar.models.EstadoCarrito;
import com.unla.reactivar.repositories.EstadoCarritoRepository;
import com.unla.reactivar.vo.EstadoCarritoVo;

@Service
@Transactional(readOnly = true)
public class EstadoCarritoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private EstadoCarritoRepository repository;

	public EstadoCarrito traerEstadoCarritoPorId(Long id) {
		log.info("Se traera un estado carrito por id");
		return repository.findByIdEstadoCarrito(id);
	}

	public List<EstadoCarrito> traerTodosEstadosCarrito() {
		log.info("Se traeran todos los estados carritos");
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoCarrito(long id) {
		EstadoCarrito registro = repository.findByIdEstadoCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoCarrito");
		}

		log.info("Se eliminara el estado carrito [{}]", id);
		repository.delete(registro);
	}

	@Transactional
	public EstadoCarrito actualizarEstadoCarrito(Long id, EstadoCarritoVo estadoCarritoVo) {
		EstadoCarrito estado = repository.findByIdEstadoCarrito(id);

		if (estado == null) {
			throw new ObjectNotFound("EstadoCarrito");
		}

		estado.setNombre(estadoCarritoVo.getNombreEstado());

		try {
			log.info("Se actualizara el estado carrito [{}]", estado.getNombre());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

	@Transactional
	public EstadoCarrito crearEstadoCarrito(EstadoCarritoVo estadoCarritoVo) {
		EstadoCarrito estado = new EstadoCarrito();

		estado.setNombre(estadoCarritoVo.getNombreEstado());

		try {
			log.info("Se creara el estado carrito [{}]", estado.getNombre());
			estado = repository.save(estado);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return estado;
	}

}
