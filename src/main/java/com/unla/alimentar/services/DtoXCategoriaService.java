package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.DtoXCategoria;
import com.unla.alimentar.repositories.DtoXCategoriaRepository;
import com.unla.alimentar.vo.DtoXCategoriaVo;

@Service
@Transactional(readOnly = true)
public class DtoXCategoriaService {

	@Autowired
	private DtoXCategoriaRepository repository;

	public DtoXCategoria traerDtoXCategoriaPorId(Long id) {
		return repository.findByIdDtoXCategoria(id);
	}

	public List<DtoXCategoria> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXCategoria(long id) {
		DtoXCategoria registro = repository.findByIdDtoXCategoria(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXCategoria");
		}

		repository.delete(registro);
	}

	public DtoXCategoria actualizarDtoXCategoria(Long id, DtoXCategoriaVo dtoXCategoriaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public DtoXCategoria crearDtoXCategoria(DtoXCategoriaVo dtoXCategoriaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
