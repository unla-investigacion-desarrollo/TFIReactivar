package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.repositories.LlevaPagaRepository;
import com.unla.alimentar.vo.LlevaPagaVo;

@Service
@Transactional(readOnly = true)
public class LlevaPagaService {

	@Autowired
	private LlevaPagaRepository repository;

	public Promocion traerLlevaPagaPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarLlevaPaga(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}

		repository.delete(registro);
	}

	public Promocion actualizarLlevaPaga(Long id, LlevaPagaVo llevaPagaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion crearLlevaPaga(LlevaPagaVo llevaPagaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
