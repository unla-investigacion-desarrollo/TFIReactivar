package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.repositories.DtoXCategoriaRepository;
import com.unla.alimentar.vo.DtoXCategoriaVo;

@Service
@Transactional(readOnly = true)
public class DtoXCategoriaService {

	@Autowired
	private DtoXCategoriaRepository repository;

	public Promocion traerDtoXCategoriaPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<Promocion> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXCategoria(long id) {
		Promocion registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXCategoria");
		}

		repository.delete(registro);
	}

	public Promocion actualizarDtoXCategoria(Long id, DtoXCategoriaVo dtoXCategoriaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Promocion crearDtoXCategoria(DtoXCategoriaVo dtoXCategoriaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
