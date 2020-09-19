package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.repositories.CategoriaRepository;
import com.unla.reactivar.vo.CategoriaVo;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria traerCategoriaPorId(Long id) {
		return repository.findByIdCategoria(id);
	}

	public List<Categoria> traerTodasCategorias() {
		return repository.findAll();
	}

	@Transactional
	public void borrarCategoria(long id) {
		Categoria registro = repository.findByIdCategoria(id);

		if (registro == null) {
			throw new ObjectNotFound("Categoria");
		}

		repository.delete(registro);
	}

	@Transactional
	public Categoria actualizarCategoria(Long id, CategoriaVo categoriaVo) {
		Categoria categoria = repository.findByIdCategoria(id);

		if (categoria == null) {
			throw new ObjectNotFound("Categoria");
		}

		categoria.setNombre(categoriaVo.getNombre());

		try {
			categoria = repository.save(categoria);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return categoria;
	}

	@Transactional
	public Categoria crearCategoria(CategoriaVo categoriaVo) {
		Categoria categoria = new Categoria();

		categoria.setNombre(categoriaVo.getNombre());

		try {
			categoria = repository.save(categoria);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return categoria;
	}

}
