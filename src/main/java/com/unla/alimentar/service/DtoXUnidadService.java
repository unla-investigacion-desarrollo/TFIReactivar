package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.DtoXUnidad;
import com.unla.alimentar.repository.DtoXUnidadRepository;

@Service
@Transactional(readOnly = true)
public class DtoXUnidadService {

	@Autowired
	private DtoXUnidadRepository repository;

	public DtoXUnidad traerDtoXUnidadPorId(Long id) {
		return repository.findByIdDtoXUnidad(id);
	}

	public List<DtoXUnidad> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXUnidad(long id) {
		DtoXUnidad registro = repository.findByIdDtoXUnidad(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXUnidad");
		}

		repository.delete(registro);
	}

}
