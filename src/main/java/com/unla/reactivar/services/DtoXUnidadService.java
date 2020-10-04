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
import com.unla.reactivar.models.DtoXUnidad;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.repositories.DtoXUnidadRepository;
import com.unla.reactivar.vo.DtoXUnidadVo;

@Service
@Transactional(readOnly = true)
public class DtoXUnidadService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private DtoXUnidadRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public DtoXUnidad traerDtoXUnidadPorId(Long id) {
		log.info("Se traera un DtoXUnidad por id");
		return repository.findByIdPromocion(id);
	}

	public List<DtoXUnidad> traerTodosDtosXUnidades() {
		log.info("Se traeran todos los dtos por unidad");
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXUnidad(long id) {
		DtoXUnidad registro = repository.findByIdPromocion(id);

		if (registro == null) {
			log.error("Se obtuvo un error al intentar borrar el dto x unidad [{}]", id);
			throw new ObjectNotFound("DtoXUnidad");
		}

		log.info("Se eliminara promocion [{}]", registro.getIdPromocion());
		repository.deletePromocion(id);
	}

	@Transactional
	public DtoXUnidad actualizarDtoXUnidad(Long id, DtoXUnidadVo dtoXUnidadVo) {
		DtoXUnidad dto = repository.findByIdPromocion(id);

		if (dto == null) {
			log.error("Se obtuvo un error al intentar actualizar el dto [{}]", id);
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXPorcentaje(dto, dtoXUnidadVo);

		try {
			log.info("Se actualizara dto x unidad");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	@Transactional
	public DtoXUnidad crearDtoXUnidad(DtoXUnidadVo dtoXUnidadVo) {
		DtoXUnidad dto = new DtoXUnidad();

		adaptVoToDtoXPorcentaje(dto, dtoXUnidadVo);

		try {
			log.info("Se creara dto x categoria");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	private void adaptVoToDtoXPorcentaje(DtoXUnidad dto, DtoXUnidadVo dtoXUnidadVo) {
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(dtoXUnidadVo.getIdEmprendimiento());

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		dto.setDescripcion(dtoXUnidadVo.getDescripcion());
		dto.setPorcDescuento(dtoXUnidadVo.getPorcDescuento());
		dto.setHabilitada(dtoXUnidadVo.isHabilitada());
		dto.setFechaFin(dtoXUnidadVo.getFechaFin());
		dto.setFechaInicio(dtoXUnidadVo.getFechaInicio());
		dto.setEmprendimiento(emprendimiento);
		dto.setCantidad(dtoXUnidadVo.getCantidad());
	}

}
