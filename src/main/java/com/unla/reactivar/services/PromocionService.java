package com.unla.reactivar.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.repositories.PromocionRepository;

@Service
@Transactional(readOnly = true)
public class PromocionService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private PromocionRepository repository;

	public Promocion traerPromocionPorId(Long id) {
		log.info("Se traera una promocion por id");
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodasPromociones() {
		log.info("Se traera todas las promociones");
		return repository.findAll();
	}

	@Transactional
	public void borrarPromocion(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("Promocion");
		}
		log.info("Se eliminara promocion [{}]", registro.getIdPromocion());

		repository.deletePromocion(id);
	}

}
