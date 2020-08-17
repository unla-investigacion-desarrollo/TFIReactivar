package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Funcion;
import com.unla.alimentar.repository.FuncionRepository;

@Service
@Transactional(readOnly = true)
public class FuncionService {

	@Autowired
	private FuncionRepository repository;

	public Funcion traerFuncionPorId(Long id) {
		return repository.findByIdFuncion(id);
	}

	public List<Funcion> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarFuncion(long id) {
		Funcion registro = repository.findByIdFuncion(id);

		if (registro == null) {
			throw new ObjectNotFound("Funcion");
		}

		repository.delete(registro);
	}

}
