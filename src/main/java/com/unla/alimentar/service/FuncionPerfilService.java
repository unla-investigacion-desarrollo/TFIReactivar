package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.FuncionPerfil;
import com.unla.alimentar.repository.FuncionPerfilRepository;

@Service
@Transactional(readOnly = true)
public class FuncionPerfilService {

	@Autowired
	private FuncionPerfilRepository repository;

	public FuncionPerfil traerFuncionPerfilPorId(Long id) {
		return repository.findByIdFuncionPerfil(id);
	}

	public List<FuncionPerfil> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarFuncionPerfil(long id) {
		FuncionPerfil registro = repository.findByIdFuncionPerfil(id);

		if (registro == null) {
			throw new ObjectNotFound("FuncionPerfil");
		}

		repository.delete(registro);
	}

}
