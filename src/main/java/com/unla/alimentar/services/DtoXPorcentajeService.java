package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.repositories.DtoXPorcentajeRepository;
import com.unla.alimentar.vo.DtoXPorcentajeVo;

@Service
@Transactional(readOnly = true)
public class DtoXPorcentajeService {

	@Autowired
	private DtoXPorcentajeRepository repository;

	public Promocion traerDtoXPorcentajePorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXPorcentaje(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXPorcentaje");
		}

		repository.delete(registro);
	}

	public Promocion actualizarDtoXPorcentaje(Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion crearDtoXPorcentaje(DtoXPorcentajeVo dtoXPorcentajeVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
