package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.DtoXPorcentaje;
import com.unla.alimentar.repositories.DtoXPorcentajeRepository;
import com.unla.alimentar.vo.DtoXPorcentajeVo;

@Service
@Transactional(readOnly = true)
public class DtoXPorcentajeService {

	@Autowired
	private DtoXPorcentajeRepository repository;

	public DtoXPorcentaje traerDtoXPorcentajePorId(Long id) {
		return repository.findByIdDtoXPorcentaje(id);
	}

	public List<DtoXPorcentaje> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXPorcentaje(long id) {
		DtoXPorcentaje registro = repository.findByIdDtoXPorcentaje(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXPorcentaje");
		}

		repository.delete(registro);
	}

	public DtoXPorcentaje actualizarDtoXPorcentaje(Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public DtoXPorcentaje crearDtoXPorcentaje(DtoXPorcentajeVo dtoXPorcentajeVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
