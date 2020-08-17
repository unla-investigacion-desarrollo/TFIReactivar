package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Categoria;
import com.unla.alimentar.repositories.CategoriaRepository;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria traerCategoriaPorId(Long id) {
		return repository.findByIdCategoria(id);
	}

	public List<Categoria> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarCategoria(long id) {
		Categoria registro = repository.findByIdCategoria(id);

		if (registro == null) {
			throw new ObjectNotFound("Categoria");
		}

		repository.delete(registro);
	}

}
