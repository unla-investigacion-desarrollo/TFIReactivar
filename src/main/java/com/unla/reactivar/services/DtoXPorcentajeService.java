package com.unla.reactivar.services;

import java.util.List;

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

	@Autowired
	private DtoXPorcentajeRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public DtoXPorcentaje traerDtoXPorcentajePorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<DtoXPorcentaje> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXPorcentaje(long id) {
		DtoXPorcentaje registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXPorcentaje");
		}

		repository.deletePromocion(id);
	}

	@Transactional
	public DtoXPorcentaje actualizarDtoXPorcentaje(Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = repository.findByIdPromocion(id);

		if (dto == null) {
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		try {
			dto = repository.save(dto);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return dto;
	}

	@Transactional
	public DtoXPorcentaje crearDtoXPorcentaje(DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = new DtoXPorcentaje();

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		try {
			dto = repository.save(dto);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
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
