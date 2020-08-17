package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Marca;
import com.unla.alimentar.repositories.MarcaRepository;

@Service
@Transactional(readOnly = true)
public class MarcaService {

	@Autowired
	private MarcaRepository repository;

	public Marca traerMarcaPorId(Long id) {
		return repository.findByIdMarca(id);
	}

	public List<Marca> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarMarca(long id) {
		Marca registro = repository.findByIdMarca(id);

		if (registro == null) {
			throw new ObjectNotFound("Marca");
		}

		repository.delete(registro);
	}

}
