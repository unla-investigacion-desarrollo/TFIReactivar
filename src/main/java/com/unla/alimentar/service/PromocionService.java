package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Promocion;
import com.unla.alimentar.repository.PromocionRepository;

@Service
@Transactional(readOnly = true)
public class PromocionService {

	@Autowired
	private PromocionRepository repository;

	public Promocion traerPromocionPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarPromocion(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("Promocion");
		}

		repository.delete(registro);
	}

}
