package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.ItemCarrito;
import com.unla.alimentar.repositories.ItemCarritoRepository;
import com.unla.alimentar.vo.ItemCarritoVo;

@Service
@Transactional(readOnly = true)
public class ItemCarritoService {

	@Autowired
	private ItemCarritoRepository repository;

	public ItemCarrito traerItemCarritoPorId(Long id) {
		return repository.findByIdItemCarrito(id);
	}

	public List<ItemCarrito> traerTodos() {
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

	public ItemCarrito actualizarItemCarrito(Long id, ItemCarritoVo itemCarritoVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemCarrito crearItemCarrito(ItemCarritoVo itemCarritoVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
