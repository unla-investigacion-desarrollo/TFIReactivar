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
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.repositories.CategoriaRepository;
import com.unla.reactivar.vo.CategoriaVo;

@Service
@Transactional(readOnly = true)
public class CategoriaService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private CategoriaRepository repository;

	public Categoria traerCategoriaPorId(Long id) {
		log.info("Se traera una categoria por id");
		return repository.findByIdCategoria(id);
	}

	public List<Categoria> traerTodasCategorias() {
		log.info("Se traeran todas las categorias");
		return repository.findAll();
	}

	@Transactional
	public void borrarCategoria(long id) {
		Categoria registro = repository.findByIdCategoria(id);

		if (registro == null) {
			log.error("Se obtuvo un error al intentar borrar la categoria [{}]", id);
			throw new ObjectNotFound("Categoria");
		}

		log.info("Se eliminara la categoria [{}]", registro.getNombre());
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
			log.info("Se actualizara la categoria [{}]", categoria.getNombre());
			categoria = repository.save(categoria);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento crear una categoria ya existente");
				throw new ObjectAlreadyExists();
			}
		}

		return categoria;
	}

	@Transactional
	public Categoria crearCategoria(CategoriaVo categoriaVo) {
		Categoria categoria = new Categoria();

		categoria.setNombre(categoriaVo.getNombre());

		try {
			log.info("Se creara la categoria [{}]", categoria.getNombre());
			categoria = repository.save(categoria);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento crear una categoria ya existente");
				throw new ObjectAlreadyExists();
			}
		}

		return categoria;
	}

}
