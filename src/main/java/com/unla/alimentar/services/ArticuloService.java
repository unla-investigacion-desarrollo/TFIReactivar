package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Articulo;
import com.unla.alimentar.repositories.ArticuloRepository;

@Service
@Transactional(readOnly = true)
public class ArticuloService {

	@Autowired
	private ArticuloRepository repository;

	public Articulo traerArticuloPorId(Long id) {
		return repository.findByIdArticulo(id);
	}

	public List<Articulo> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarArticulo(long id) {
		Articulo articulo = repository.findByIdArticulo(id);

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		repository.delete(articulo);
	}

}
