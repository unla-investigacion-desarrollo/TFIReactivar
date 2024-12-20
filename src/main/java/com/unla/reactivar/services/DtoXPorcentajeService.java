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
import com.unla.reactivar.models.DtoXPorcentaje;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.repositories.DtoXPorcentajeRepository;
import com.unla.reactivar.vo.DtoXPorcentajeVo;

@Service
@Transactional(readOnly = true)
public class DtoXPorcentajeService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private DtoXPorcentajeRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public DtoXPorcentaje traerDtoXPorcentajePorId(Long id) {
		log.info("Se traera un DtoXPorcentaje por id");
		return repository.findByIdPromocion(id);
	}

	public List<DtoXPorcentaje> traerTodosDtosXPorcentaje() {
		log.info("Se traeran todos los dtos por porcentaje");
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXPorcentaje(long id) {
		DtoXPorcentaje registro = repository.findByIdPromocion(id);

		if (registro == null) {
			log.error("Se obtuvo un error al intentar borrar el dto x porcentaje [{}]", id);
			throw new ObjectNotFound("DtoXPorcentaje");
		}
		log.info("Se eliminara promocion [{}]", registro.getIdPromocion());
		repository.deletePromocion(id);
	}

	@Transactional
	public DtoXPorcentaje actualizarDtoXPorcentaje(Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = repository.findByIdPromocion(id);

		if (dto == null) {
			log.error("Se obtuvo un error al intentar actualizar el carrito [{}]", id);
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		try {
			log.info("Se actualizara dto x porcentaje");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	@Transactional
	public DtoXPorcentaje crearDtoXPorcentaje(DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = new DtoXPorcentaje();

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		try {
			log.info("Se creara dto x porcentaje");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	private void adaptVoToDtoXPorcentaje(DtoXPorcentaje dto, DtoXPorcentajeVo dtoXPorcentajeVo) {
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(dtoXPorcentajeVo.getIdEmprendimiento());

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		dto.setDescripcion(dtoXPorcentajeVo.getDescripcion());
		dto.setDescuento(dtoXPorcentajeVo.getDescuento());
		dto.setHabilitada(dtoXPorcentajeVo.isHabilitada());
		dto.setFechaFin(dtoXPorcentajeVo.getFechaFin());
		dto.setFechaInicio(dtoXPorcentajeVo.getFechaInicio());
		dto.setEmprendimiento(emprendimiento);
	}

}
