package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.UnidadMedida;
import com.unla.alimentar.repository.UnidadMedidaRepository;

@Service
@Transactional(readOnly = true)
public class UnidadMedidaService {

	@Autowired
	private UnidadMedidaRepository repository;

	public UnidadMedida traerUnidadMedidaPorId(Long id) {
		return repository.findByIdUnidadMedida(id);
	}

	public List<UnidadMedida> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarUnidadMedida(long id) {
		UnidadMedida registro = repository.findByIdUnidadMedida(id);

		if (registro == null) {
			throw new ObjectNotFound("UnidadMedida");
		}

		repository.delete(registro);
	}

}
