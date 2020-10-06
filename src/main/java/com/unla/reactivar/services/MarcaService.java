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
import com.unla.reactivar.models.Marca;
import com.unla.reactivar.repositories.MarcaRepository;
import com.unla.reactivar.vo.MarcaVo;

@Service
@Transactional(readOnly = true)
public class MarcaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private MarcaRepository repository;

	@Autowired
	private FuncionPerfilService funcionPerfilService;

	public Marca traerMarcaPorId(Long id) {
		log.info("Se traeran marca por id");
		return repository.findByIdMarca(id);
	}

	public List<Marca> traerTodasMarcas() {
		log.info("Se traeran todas las marcas");
		return repository.findAll();
	}

	@Transactional
	public void borrarMarca(long id) {
		Marca registro = repository.findByIdMarca(id);

		if (registro == null) {
			throw new ObjectNotFound("Marca");
		}
		log.info("Se eliminara marca [{}]", registro.getNombre());
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
			log.info("Se actualizara marca [{}]", marca.getNombre());
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
			log.info("Se creara marca [{}]", marca.getNombre());
			marca = repository.save(marca);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return marca;
	}

	@Transactional
	public Marca crearMarca(MarcaVo marcaVo, long idPerfil, long idEndpoint) {
		Marca marca = new Marca();

		if (funcionPerfilService.concederPermiso(idPerfil, idEndpoint) == true) {
			marca.setNombre(marcaVo.getNombreMarca());

			try {
				marca = repository.save(marca);
			} catch (Exception e) {
				if (e.getCause() != null
						&& e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
					throw new ObjectAlreadyExists();
				}
			}

		} else {
			throw new ObjectNotFound("Usuario con perfil no valido para crear Marcas");
		}

		return marca;
	}
}
