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
import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.repositories.RubroRepository;
import com.unla.reactivar.vo.RubroVo;

@Service
@Transactional(readOnly = true)
public class RubroService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private RubroRepository repository;

	public Rubro traerRubroPorId(Long id) {
		log.info("Se traera rubro por id");
		Rubro rubro = repository.findByIdRubro(id);
		return rubro;
	}

	public List<Rubro> traerTodosRubros() {
		log.info("Se traeran todos los rubros");
		return repository.findAll();
	}

	@Transactional
	public void borrarRubro(long id) {
		Rubro rubro = repository.findByIdRubro(id);

		if (rubro == null) {
			throw new ObjectNotFound("Rubro");
		}
		log.info("Se eliminara rubro [{}]", rubro.getNombre());
		repository.delete(rubro);
	}

	@Transactional
	public Rubro actualizarRubro(Long id, RubroVo rubroVo) {
		Rubro rubro = repository.findByIdRubro(id);

		if (rubro == null) {
			throw new ObjectNotFound("Rubro");
		}

		rubro.setNombre(rubroVo.getRubro());

		try {
			log.info("Se actualizara rubro [{}]", rubro.getNombre());
			rubro = repository.save(rubro);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return rubro;
	}

	@Transactional
	public Rubro crearRubro(RubroVo rubroVo) {
		Rubro rubro = new Rubro();

		rubro.setNombre(rubroVo.getRubro());

		try {
			log.info("Se creara rubro [{}]", rubro.getNombre());
			rubro = repository.save(rubro);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return rubro;
	}

}
