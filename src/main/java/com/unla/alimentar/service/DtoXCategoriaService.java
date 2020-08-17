package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.DtoXCategoria;
import com.unla.alimentar.repository.DtoXCategoriaRepository;

@Service
@Transactional(readOnly = true)
public class DtoXCategoriaService {

	@Autowired
	private DtoXCategoriaRepository repository;

	public DtoXCategoria traerDtoXCategoriaPorId(Long id) {
		return repository.findByIdDtoXCategoria(id);
	}

	public List<DtoXCategoria> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXCategoria(long id) {
		DtoXCategoria registro = repository.findByIdDtoXCategoria(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXCategoria");
		}

		repository.delete(registro);
	}

}
