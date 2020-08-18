package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.repositories.PromocionRepository;
import com.unla.alimentar.vo.PromocionVo;

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

		repository.delete(registro);
	}

	public Promocion actualizarPromocion(Long id, PromocionVo promocionVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion crearPromocion(PromocionVo promocionVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
