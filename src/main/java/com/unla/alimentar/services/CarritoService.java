package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Carrito;
import com.unla.alimentar.repositories.CarritoRepository;

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
