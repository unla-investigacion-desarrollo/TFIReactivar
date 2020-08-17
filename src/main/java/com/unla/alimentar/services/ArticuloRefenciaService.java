package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.ArticuloReferencia;
import com.unla.alimentar.repositories.ArticuloReferenciaRepository;

@Service
@Transactional(readOnly = true)
public class ArticuloRefenciaService {

	@Autowired
	private ArticuloReferenciaRepository repository;

	public ArticuloReferencia traerArticuloReferenciaPorId(Long id) {
		return repository.findByIdArticuloReferencia(id);
	}

	public List<ArticuloReferencia> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarArticuloReferencia(long id) {
		ArticuloReferencia articuloReferencia = repository.findByIdArticuloReferencia(id);

		if (articuloReferencia == null) {
			throw new ObjectNotFound("ArticuloReferencia");
		}

		repository.delete(articuloReferencia);
	}

}
