package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.LlevaPaga;
import com.unla.alimentar.repositories.LlevaPagaRepository;

@Service
@Transactional(readOnly = true)
public class LlevaPagaService {

	@Autowired
	private LlevaPagaRepository repository;

	public LlevaPaga traerLlevaPagaPorId(Long id) {
		return repository.findByIdLlevaPaga(id);
	}

	public List<LlevaPaga> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarLlevaPaga(long id) {
		LlevaPaga registro = repository.findByIdLlevaPaga(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}

		repository.delete(registro);
	}

}
