package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.EstadoCarrito;
import com.unla.alimentar.repositories.EstadoCarritoRepository;

@Service
@Transactional(readOnly = true)
public class EstadoCarritoService {

	@Autowired
	private EstadoCarritoRepository repository;

	public EstadoCarrito traerEstadoCarritoPorId(Long id) {
		return repository.findByIdEstadoCarrito(id);
	}

	public List<EstadoCarrito> traerTodos(Long id) {
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

}
