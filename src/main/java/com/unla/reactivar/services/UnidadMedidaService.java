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
import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.repositories.UnidadMedidaRepository;
import com.unla.reactivar.vo.UnidadMedidaVo;

@Service
@Transactional(readOnly = true)
public class UnidadMedidaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private UnidadMedidaRepository repository;

	public UnidadMedida traerUnidadMedidaPorId(Long id) {
		log.info("Se traera unidad medida por id");
		return repository.findByIdUnidadMedida(id);
	}

	public List<UnidadMedida> traerTodasUnidadesMedida() {
		log.info("Se traeran todas las unidades medidas");
		return repository.findAll();
	}

	@Transactional
	public void borrarUnidadMedida(long id) {
		UnidadMedida registro = repository.findByIdUnidadMedida(id);

		if (registro == null) {
			throw new ObjectNotFound("UnidadMedida");
		}

		log.info("Se eliminara la unidad medida [{}]", id);
		repository.delete(registro);
	}

	@Transactional
	public UnidadMedida actualizarUnidadMedida(Long id, UnidadMedidaVo unidadMedidaVo) {
		UnidadMedida medida = repository.findByIdUnidadMedida(id);

		if (medida == null) {
			throw new ObjectNotFound("UnidadMedida");
		}

		medida.setNombre(unidadMedidaVo.getUnidadMedida());

		try {
			log.info("Se actualizara la unidad medida [{}]", medida.getNombre());
			medida = repository.save(medida);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return medida;
	}

	@Transactional
	public UnidadMedida crearUnidadMedida(UnidadMedidaVo unidadMedidaVo) {
		UnidadMedida medida = new UnidadMedida();

		medida.setNombre(unidadMedidaVo.getUnidadMedida());

		try {
			log.info("Se creara la unidad medida [{}]", medida.getNombre());
			medida = repository.save(medida);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return medida;
	}

}
