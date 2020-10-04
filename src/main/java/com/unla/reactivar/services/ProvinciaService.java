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
import com.unla.reactivar.repositories.ProvinciaRepository;
import com.unla.reactivar.vo.ProvinciaVo;

@Service
@Transactional(readOnly = true)
public class ProvinciaService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ProvinciaRepository repository;

	@Autowired
	private LocalidadService localidadService;

	public Provincia traerProvinciaPorId(Long id) {
		log.info("Se traera una prov por id");
		return repository.findByIdProvincia(id);
	}

	public List<Provincia> traerTodos() {
		log.info("Se traeran todas las prov");
		return repository.findAll();
	}

	@Transactional
	public void borrarProvincia(long id) {
		Provincia provincia = repository.findByIdProvincia(id);

		if (provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		log.info("Se eliminara provincia [{}]", provincia.getNombre());

		repository.delete(provincia);
	}

	@Transactional
	public Provincia actualizarProvincia(Long id, ProvinciaVo provinciaVo) {
		Provincia provincia = repository.findByIdProvincia(id);

		if (provincia == null) {
			throw new ObjectNotFound("Provincia");
		}

		provincia.setNombre(provinciaVo.getProvincia());

		try {
			log.info("Se actualizara provincia [{}]", provincia.getNombre());
			provincia = repository.save(provincia);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return provincia;
	}

	@Transactional
	public Provincia crearProvincia(ProvinciaVo provinciaVo) {
		Provincia provincia = new Provincia();

		provincia.setNombre(provinciaVo.getProvincia());

		try {
			log.info("Se creara provincia [{}]", provincia.getNombre());
			provincia = repository.save(provincia);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return provincia;
	}

	public List<Localidad> traerLocalidades(Long id) {
		Provincia provincia = repository.findByIdProvincia(id);

		if (provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		log.info("Se traeran las localidades de la provincia [{}]", provincia.getNombre());

		return localidadService.traerLocalidadesPorProvincia(id);
	}

}
