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
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.repositories.TipoEmprendimientoRepository;
import com.unla.reactivar.vo.TipoEmprendimientoVo;

@Service
@Transactional(readOnly = true)
public class TipoEmprendimientoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private TipoEmprendimientoRepository repository;

	public TipoEmprendimiento traerTipoEmprendimientoPorId(Long id) {
		log.info("Se traera un tipo Emprendimiento por id");
		return repository.findByIdTipoEmprendimiento(id);
	}

	public List<TipoEmprendimiento> traerTodos() {
		log.info("Se traeran todos los tipo emprendimientos");
		return repository.findAll();
	}

	@Transactional
	public void borrarTipoEmprendimiento(long id) {
		TipoEmprendimiento tipoEmprendimiento = repository.findByIdTipoEmprendimiento(id);

		if (tipoEmprendimiento == null) {
			throw new ObjectNotFound("TipoEmprendimiento");
		}
		log.info("Se eliminara tipo emprendimiento [{}]", tipoEmprendimiento.getNombre());
		repository.delete(tipoEmprendimiento);
	}

	@Transactional
	public TipoEmprendimiento actualizarTipoEmprendimiento(Long id, TipoEmprendimientoVo tipoEmprendimientoVo) {
		TipoEmprendimiento tipoEmprendimiento = repository.findByIdTipoEmprendimiento(id);

		if (tipoEmprendimiento == null) {
			throw new ObjectNotFound("TipoEmprendimiento");
		}

		tipoEmprendimiento.setNombre(tipoEmprendimientoVo.getTipoEmprendimiento());

		try {
			log.info("Se actualizara tipo emprendimiento [{}]", tipoEmprendimiento.getNombre());
			tipoEmprendimiento = repository.save(tipoEmprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return tipoEmprendimiento;
	}

	@Transactional
	public TipoEmprendimiento crearTipoEmprendimiento(TipoEmprendimientoVo tipoEmprendimientoVo) {
		TipoEmprendimiento tipoEmprendimiento = new TipoEmprendimiento();

		tipoEmprendimiento.setNombre(tipoEmprendimientoVo.getTipoEmprendimiento());

		try {
			log.info("Se creara tipo emprendimiento [{}]", tipoEmprendimiento.getNombre());
			tipoEmprendimiento = repository.save(tipoEmprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return tipoEmprendimiento;
	}

}
