package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.UnidadMedida;
import com.unla.alimentar.repositories.UnidadMedidaRepository;
import com.unla.alimentar.vo.UnidadMedidaVo;

@Service
@Transactional(readOnly = true)
public class UnidadMedidaService {

	@Autowired
	private UnidadMedidaRepository repository;

	public UnidadMedida traerUnidadMedidaPorId(Long id) {
		return repository.findByIdUnidadMedida(id);
	}

	public List<UnidadMedida> traerTodos() {
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

	public UnidadMedida actualizarUnidadMedida(Long id, UnidadMedidaVo unidadMedidaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public UnidadMedida crearUnidadMedida(UnidadMedidaVo unidadMedidaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
