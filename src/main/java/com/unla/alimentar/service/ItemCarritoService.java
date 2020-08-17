package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.ItemCarrito;
import com.unla.alimentar.repository.ItemCarritoRepository;

@Service
@Transactional(readOnly = true)
public class ItemCarritoService {

	@Autowired
	private ItemCarritoRepository repository;

	public ItemCarrito traerItemCarritoPorId(Long id) {
		return repository.findByIdItemCarrito(id);
	}

	public List<ItemCarrito> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarItemCarrito(long id) {
		ItemCarrito registro = repository.findByIdItemCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("ItemCarrito");
		}

		repository.delete(registro);
	}

}
