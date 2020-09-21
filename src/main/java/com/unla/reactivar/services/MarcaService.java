package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Marca;
import com.unla.reactivar.repositories.MarcaRepository;
import com.unla.reactivar.vo.MarcaVo;

@Service
@Transactional(readOnly = true)
public class MarcaService {

	@Autowired
	private MarcaRepository repository;

	public Marca traerMarcaPorId(Long id) {
		return repository.findByIdMarca(id);
	}

	public List<Marca> traerTodasMarcas() {
		return repository.findAll();
	}

	@Transactional
	public void borrarMarca(long id) {
		Marca registro = repository.findByIdMarca(id);

		if (registro == null) {
			throw new ObjectNotFound("Marca");
		}

		repository.delete(registro);
	}

	@Transactional
	public Marca actualizarMarca(Long id, MarcaVo marcaVo) {
		Marca marca = repository.findByIdMarca(id);

		if (marca == null) {
			throw new ObjectNotFound("Marca");
		}

		marca.setNombre(marcaVo.getNombreMarca());

		try {
			marca = repository.save(marca);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return marca;
	}

	@Transactional
	public Marca crearMarca(MarcaVo marcaVo) {
		Marca marca = new Marca();

		marca.setNombre(marcaVo.getNombreMarca());

		try {
			marca = repository.save(marca);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return marca;
	}

}
