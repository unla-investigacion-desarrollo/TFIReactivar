package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.DtoXUnidad;
import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.repositories.DtoXUnidadRepository;
import com.unla.alimentar.vo.DtoXUnidadVo;

@Service
@Transactional(readOnly = true)
public class DtoXUnidadService {

	@Autowired
	private DtoXUnidadRepository repository;

	public Promocion traerDtoXUnidadPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXUnidad(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXUnidad");
		}

		repository.delete(registro);
	}

	public Promocion actualizarDtoXUnidad(Long id, DtoXUnidadVo dtoXUnidadVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion crearDtoXUnidad(DtoXUnidadVo dtoXUnidadVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
