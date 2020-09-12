package com.unla.reactivar.services;

import java.util.List;

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

	@Autowired
	private EstadoCarritoRepository repository;

	public EstadoCarrito traerEstadoCarritoPorId(Long id) {
		return repository.findByIdEstadoCarrito(id);
	}

	public List<EstadoCarrito> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarEstadoCarrito(long id) {
		EstadoCarrito registro = repository.findByIdEstadoCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("EstadoCarrito");
		}

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
			estado = repository.save(estado);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return estado;
	}

	@Transactional
	public EstadoCarrito crearEstadoCarrito(EstadoCarritoVo estadoCarritoVo) {
		EstadoCarrito estado = new EstadoCarrito();

		estado.setNombre(estadoCarritoVo.getNombreEstado());

		try {
			estado = repository.save(estado);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return estado;
	}

}
