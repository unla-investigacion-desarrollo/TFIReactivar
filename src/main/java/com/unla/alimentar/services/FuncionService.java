package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Funcion;
import com.unla.alimentar.repositories.FuncionRepository;
import com.unla.alimentar.vo.FuncionVo;

@Service
@Transactional(readOnly = true)
public class FuncionService {

	@Autowired
	private FuncionRepository repository;

	public Funcion traerFuncionPorId(Long id) {
		return repository.findByIdFuncion(id);
	}

	public List<Funcion> traerTodos() {
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

	@Transactional
	public Funcion actualizarFuncion(Long id, FuncionVo funcionVo) {
		Funcion funcion = repository.findByIdFuncion(id);

		if (funcion == null) {
			throw new ObjectNotFound("Funcion");
		}
		
		funcion.setDescripcion(funcionVo.getDescripcion());
		
		return repository.save(funcion);
	}

	@Transactional
	public Funcion crearFuncion(FuncionVo funcionVo) {
		Funcion funcion = new Funcion();
		
		funcion.setDescripcion(funcionVo.getDescripcion());
		
		return repository.save(funcion);
	}

}
