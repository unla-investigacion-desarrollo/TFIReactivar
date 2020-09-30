package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.UbicacionRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.UbicacionVo;

@Service
@Transactional(readOnly = true)
public class UbicacionService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private UbicacionRepository repository;

	@Autowired
	private LocalidadService localidadService;

	public Ubicacion traerUbicacionPorId(Long id) {
		log.info("Se traera ubicacion por id");
		return repository.findByIdUbicacion(id);
	}

	@Transactional
	public Ubicacion crearUbicacion(UbicacionVo ubicacionVo) {
		Ubicacion ubicacion = new Ubicacion();

		adaptVoToUbicacion(ubicacion, ubicacionVo);

		try {
			log.info("Se creara ubicacion");
			ubicacion = repository.save(ubicacion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return ubicacion;
	}

	public List<Ubicacion> traerTodos() {
		log.info("Se traeran todas las ubicaciones");
		return repository.findAll();
	}

	@Transactional
	public void borrarUbicacion(long id) {
		Ubicacion ubicacion = repository.findByIdUbicacion(id);

		if (ubicacion == null) {
			throw new ObjectNotFound("Ubicacion");
		}

		log.info("Se eliminara la ubicacion [{}]", id);
		repository.delete(ubicacion);
	}

	@Transactional
	public Ubicacion actualizarUbicacion(Long id, UbicacionVo ubicacionVo) {
		Ubicacion ubicacion = repository.findByIdUbicacion(id);

		if (ubicacion == null) {
			throw new ObjectNotFound("Ubicacion");
		}

		adaptVoToUbicacion(ubicacion, ubicacionVo);

		try {
			log.info("Se actualizara ubicacion");
			ubicacion = repository.save(ubicacion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return ubicacion;
	}

	private void adaptVoToUbicacion(Ubicacion ubicacion, UbicacionVo ubicacionVo) {
		ubicacion.setCalle(ubicacionVo.getCalle());
		ubicacion.setNumero(ubicacionVo.getNumero());
		if (!StringUtils.isBlank(ubicacionVo.getDepartamento()))
			ubicacion.setDepartamento(ubicacionVo.getDepartamento());
		if (new Integer(ubicacionVo.getPiso()) != null || ubicacionVo.getPiso() != 0)
			ubicacion.setPiso(ubicacionVo.getPiso());
		ubicacion.setLatitud(ubicacionVo.getLatitud());
		ubicacion.setLongitud(ubicacionVo.getLongitud());
		ubicacion.setUsuarioModi(ubicacionVo.getUsuarioModi());
		ubicacion.setFechaModi(DateUtils.fechaHoy());
		Localidad localidad = localidadService.traerLocalidadPorId(ubicacionVo.getIdLocalidad());
		if (localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		ubicacion.setLocalidad(localidad);
	}

}
