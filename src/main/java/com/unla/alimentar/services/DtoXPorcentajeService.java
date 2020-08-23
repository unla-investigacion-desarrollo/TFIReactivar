package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.DtoXPorcentaje;
import com.unla.alimentar.models.Emprendimiento;
import com.unla.alimentar.repositories.DtoXPorcentajeRepository;
import com.unla.alimentar.vo.DtoXPorcentajeVo;

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

		repository.delete(registro);
	}

	@Transactional
	public DtoXPorcentaje actualizarDtoXPorcentaje(Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = repository.findByIdPromocion(id);

		if (dto == null) {
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		return repository.save(dto);
	}

	@Transactional
	public DtoXPorcentaje crearDtoXPorcentaje(DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dto = new DtoXPorcentaje();

		adaptVoToDtoXPorcentaje(dto, dtoXPorcentajeVo);

		return null;
	}

	private void adaptVoToDtoXPorcentaje(DtoXPorcentaje dto, DtoXPorcentajeVo dtoXPorcentajeVo) {
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(dtoXPorcentajeVo.getIdEmprendimiento());

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
