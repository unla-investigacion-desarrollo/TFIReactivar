package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.DtoXPorcentaje;
import com.unla.alimentar.repository.DtoXPorcentajeRepository;

@Service
@Transactional(readOnly = true)
public class DtoXPorcentajeService {

	@Autowired
	private DtoXPorcentajeRepository repository;

	public DtoXPorcentaje traerDtoXPorcentajePorId(Long id) {
		return repository.findByIdDtoXPorcentaje(id);
	}

	public List<DtoXPorcentaje> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXPorcentaje(long id) {
		DtoXPorcentaje registro = repository.findByIdDtoXPorcentaje(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXPorcentaje");
		}

		repository.delete(registro);
	}

}
