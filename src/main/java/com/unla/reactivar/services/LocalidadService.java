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
import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.models.Provincia;
import com.unla.reactivar.repositories.LocalidadRepository;
import com.unla.reactivar.vo.LocalidadVo;

@Service
@Transactional(readOnly = true)
public class LocalidadService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private LocalidadRepository repository;

	@Autowired
	private ProvinciaService provinciaService;

	public Localidad traerLocalidadPorId(Long id) {
		log.info("Se traera localidad por id");
		return repository.findByIdLocalidad(id);
	}

	public List<Localidad> traerTodasLocalidades() {
		log.info("Se traeran todas las localidades");
		return repository.findAll();
	}

	@Transactional
	public void borrarLocalidad(long id) {
		Localidad localidad = repository.findByIdLocalidad(id);

		if (localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		log.info("Se eliminara localidad [{}]",localidad.getNombre());

		repository.delete(localidad);
	}

	@Transactional
	public Localidad actualizarLocalidad(Long id, LocalidadVo localidadVo) {
		Localidad localidad = repository.findByIdLocalidad(id);

		if (localidad == null) {
			throw new ObjectNotFound("Localidad");
		}

		adaptVoToLocalidad(localidad, localidadVo);

		try {
			log.info("Se actualizara localidad [{}]",localidad.getNombre());
			localidad = repository.save(localidad);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return localidad;
	}

	@Transactional
	public Localidad crearLocalidad(LocalidadVo localidadVo) {
		Localidad localidad = new Localidad();

		adaptVoToLocalidad(localidad, localidadVo);

		try {
			log.info("Se creara localidad [{}]",localidad.getNombre());
			localidad = repository.save(localidad);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return localidad;
	}

	private void adaptVoToLocalidad(Localidad localidad, LocalidadVo localidadVo) {
		Provincia provincia = provinciaService.traerProvinciaPorId(localidadVo.getIdProvincia());

		if (provincia == null) {
			throw new ObjectNotFound("Provincia");
		}

		localidad.setProvincia(provincia);
		localidad.setNombre(localidadVo.getLocalidad());
	}

	public List<Localidad> traerLocalidadesPorProvincia(Long idProvincia) {
		log.info("Se traera localidades por provincia [{}]",idProvincia);
		return repository.findAllByProvincia(idProvincia);
	}
}
