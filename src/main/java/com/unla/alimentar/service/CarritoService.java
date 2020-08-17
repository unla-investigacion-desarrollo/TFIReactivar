package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Carrito;
import com.unla.alimentar.repository.CarritoRepository;

@Service
@Transactional(readOnly = true)
public class CarritoService {

	@Autowired
	private CarritoRepository repository;

	public Carrito traerCarritoPorId(Long id) {
		return repository.findByIdCarrito(id);
	}

	public List<Carrito> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarCarrito(long id) {
		Carrito registro = repository.findByIdCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("Carrito");
		}

		repository.delete(registro);
	}

}
