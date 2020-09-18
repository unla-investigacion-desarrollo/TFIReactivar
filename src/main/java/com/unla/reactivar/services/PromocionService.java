package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.repositories.PromocionRepository;

@Service
@Transactional(readOnly = true)
public class PromocionService {

	@Autowired
	private PromocionRepository repository;

	public Promocion traerPromocionPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarPromocion(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("Promocion");
		}

		repository.deletePromocion(id);
	}

}
