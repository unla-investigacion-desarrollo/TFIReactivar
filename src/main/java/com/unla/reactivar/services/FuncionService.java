package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private FuncionRepository repository;

	public Funcion traerFuncionPorId(Long id) {
		log.info("Se traera funcion por id");

		return repository.findByIdFuncion(id);
	}

	public List<Funcion> traerTodasFunciones() {
		log.info("Se traeran todas las funciones");
		return repository.findAll();
	}

	@Transactional
	public void borrarFuncion(long id) {
		Funcion registro = repository.findByIdFuncion(id);

		if (registro == null) {
			throw new ObjectNotFound("Funcion");
		}
		log.info("Se eliminara funcion");
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
			log.info("Se actualizara funcion");
			funcion = repository.save(funcion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return funcion;
	}

	@Transactional
	public Funcion crearFuncion(FuncionVo funcionVo) {
		Funcion funcion = new Funcion();

		funcion.setDescripcion(funcionVo.getDescripcion());

		try {
			log.info("Se creara funcion");
			funcion = repository.save(funcion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return funcion;
	}

}
