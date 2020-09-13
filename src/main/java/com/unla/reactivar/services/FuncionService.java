package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.repositories.FuncionRepository;
import com.unla.reactivar.vo.FuncionVo;

@Service
@Transactional(readOnly = true)
public class FuncionService {

	@Autowired
	private FuncionRepository repository;

	public Funcion traerFuncionPorId(Long id) {
		return repository.findByIdFuncion(id);
	}

	public List<Funcion> traerTodasFunciones() {
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

		try {
			funcion = repository.save(funcion);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return funcion;
	}

	@Transactional
	public Funcion crearFuncion(FuncionVo funcionVo) {
		Funcion funcion = new Funcion();

		funcion.setDescripcion(funcionVo.getDescripcion());

		try {
			funcion = repository.save(funcion);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return funcion;
	}

}
