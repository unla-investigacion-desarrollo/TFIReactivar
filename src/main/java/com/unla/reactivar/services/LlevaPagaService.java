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
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.LlevaPaga;
import com.unla.reactivar.repositories.LlevaPagaRepository;
import com.unla.reactivar.vo.LlevaPagaVo;

@Service
@Transactional(readOnly = true)
public class LlevaPagaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private LlevaPagaRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public LlevaPaga traerLlevaPagaPorId(Long id) {
		log.info("Se traera la promocion lleva y paga por id");
		return repository.findByIdPromocion(id);
	}

	public List<LlevaPaga> traerTodosLlevaPaga() {
		log.info("Se traeran todas las promociones lleva y paga");
		return repository.findAll();
	}

	@Transactional
	public void borrarLlevaPaga(long id) {
		LlevaPaga registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}
		log.info("Se eliminara lleva paga[{}]", id);

		repository.deletePromocion(id);
	}

	@Transactional
	public LlevaPaga actualizarLlevaPaga(Long id, LlevaPagaVo llevaPagaVo) {
		LlevaPaga registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}

		adaptVoToDtoXPorcentaje(registro, llevaPagaVo);

		try {
			log.info("Se actualizara lleva paga");
			registro = repository.save(registro);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return registro;
	}

	@Transactional
	public LlevaPaga crearLlevaPaga(LlevaPagaVo llevaPagaVo) {
		LlevaPaga dto = new LlevaPaga();

		adaptVoToDtoXPorcentaje(dto, llevaPagaVo);

		try {
			log.info("Se creara lleva paga");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	private void adaptVoToDtoXPorcentaje(LlevaPaga dto, LlevaPagaVo llevaPagaVo) {
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(llevaPagaVo.getIdEmprendimiento());

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		dto.setDescripcion(llevaPagaVo.getDescripcion());
		dto.setHabilitada(llevaPagaVo.isHabilitada());
		dto.setFechaFin(llevaPagaVo.getFechaFin());
		dto.setFechaInicio(llevaPagaVo.getFechaInicio());
		dto.setEmprendimiento(emprendimiento);
		dto.setLleva(llevaPagaVo.getLleva());
		dto.setPaga(llevaPagaVo.getPaga());
	}

}
