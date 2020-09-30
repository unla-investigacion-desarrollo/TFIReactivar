package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.repositories.OcupacionLocalRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.OcupacionLocalDniVo;
import com.unla.reactivar.vo.OcupacionLocalVo;
import com.unla.reactivar.vo.PersonaFisicaVo;

@Service
@Transactional(readOnly = true)
public class OcupacionLocalService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private OcupacionLocalRepository repository;

	@Autowired
	private PersonaFisicaService personaFisicaService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public OcupacionLocal traerOcupacionLocalPorId(Long id) {
		log.info("Se traeran ocupacion local por id");
		return repository.findByIdOcupacionLocal(id);
	}

	public List<OcupacionLocal> traerTodasOcupacionesLocales() {
		log.info("Se traeran todas las ocupaciones local");
		return repository.findAll();
	}

	@Transactional
	public void borrarOcupacionLocal(long id) {
		OcupacionLocal registro = repository.findByIdOcupacionLocal(id);

		if (registro == null) {
			throw new ObjectNotFound("OcupacionLocal");
		}
		log.info("Se eliminara Ocupacion local");

		repository.delete(registro);
	}

	@Transactional
	public OcupacionLocal actualizarOcupacionLocal(Long id, OcupacionLocalVo ocupacionLocalVo) {
		OcupacionLocal ocupacion = repository.findByIdOcupacionLocal(id);

		if (ocupacion == null) {
			throw new ObjectNotFound("OcupacionLocal");
		}

		adaptVoToOcupacionLocal(ocupacion, ocupacionLocalVo);

		try {
			log.info("Se actualizara Ocupacion local");
			ocupacion = repository.save(ocupacion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return ocupacion;
	}

	@Transactional
	public OcupacionLocal crearOcupacionLocal(OcupacionLocalVo ocupacionLocalVo) {
		OcupacionLocal ocupacion = repository.findByEmprendimientoPersona(ocupacionLocalVo.getIdEmprendimiento(),
				ocupacionLocalVo.getIdPersona());

		if (ocupacion != null) {
			log.info("Se marca salida Ocupacion local emprendimiento [{}]", ocupacion.getEmprendimiento().getNombre());
			ocupacion.setFechaHoraSalida(DateUtils.fechaHoy());
			ocupacion.setFechaModi(DateUtils.fechaHoy());
			ocupacion.setUsuarioModi(ocupacionLocalVo.getUsuarioModi());
		} else {
			log.info("Se marca entrada Ocupacion local emprendimiento [{}]", ocupacionLocalVo.getIdEmprendimiento());
			ocupacion = new OcupacionLocal();
			ocupacion.setFechaHoraEntrada(DateUtils.fechaHoy());
			adaptVoToOcupacionLocal(ocupacion, ocupacionLocalVo);
		}

		try {
			ocupacion = repository.save(ocupacion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return ocupacion;
	}

	private void adaptVoToOcupacionLocal(OcupacionLocal ocupacion, OcupacionLocalVo ocupacionLocalVo) {
		PersonaFisica persona = personaFisicaService.traerPersonaFisicaPorId(ocupacionLocalVo.getIdPersona());
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(ocupacionLocalVo.getIdEmprendimiento());

		if (persona == null || emprendimiento == null) {
			throw new ObjectNotFound("Persona/Emprendimiento");
		}

		ocupacion.setEmprendimiento(emprendimiento);
		ocupacion.setPersona(persona);
		ocupacion.setFechaModi(DateUtils.fechaHoy());
		ocupacion.setUsuarioModi(ocupacionLocalVo.getUsuarioModi());
	}

	private void adaptVoToOcupacionLocalDni(OcupacionLocal ocupacion, OcupacionLocalVo ocupacionLocalVo,
			PersonaFisica persona) {
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(ocupacionLocalVo.getIdEmprendimiento());

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		ocupacion.setEmprendimiento(emprendimiento);
		ocupacion.setPersona(persona);
		ocupacion.setFechaModi(DateUtils.fechaHoy());
		ocupacion.setUsuarioModi(ocupacionLocalVo.getUsuarioModi());
	}

	public List<OcupacionLocal> traerOcupacionEntreFechas(Date fechaInicio, Date fechaFin, long idPersona) {
		List<OcupacionLocal> ocupacionesLocal = repository.findByFechaDesdeHasta(fechaInicio, fechaFin);
		List<Long> listaEmprendimientosVisitados = new ArrayList<>();
		List<OcupacionLocal> ocupacionesLocalFiltrado = new ArrayList<>();
		log.info("Se traeran las ocupaciones locales entre fechas");

		for (OcupacionLocal ocupacion : ocupacionesLocal) {
			if (ocupacion.getPersona().getIdPersona() == idPersona) {
				listaEmprendimientosVisitados.add(ocupacion.getEmprendimiento().getIdEmprendimiento());
			}
		}
		listaEmprendimientosVisitados = listaEmprendimientosVisitados.stream().distinct().collect(Collectors.toList());
		for (OcupacionLocal ocupacion : ocupacionesLocal) {
			for (long idEmprendimiento : listaEmprendimientosVisitados) {
				if (ocupacion.getEmprendimiento().getIdEmprendimiento() == idEmprendimiento) {
					ocupacionesLocalFiltrado.add(ocupacion);
					break;
				}
			}
		}

		return ocupacionesLocalFiltrado;
	}

	public List<OcupacionLocal> traerOcupacionesSinSalida() {
		log.info("Se traeran las ocupaciones locales sin salida");

		return repository.findByFechaHoraSalidaIsNull();
	}

	@Transactional
	public void actualizarFechaSalidaVacia(OcupacionLocal ocupacion) {
		repository.save(ocupacion);
	}

	@Transactional
	public OcupacionLocal crearOcupacionLocalDni(String idEmprendimientoBase64, OcupacionLocalDniVo ocupacionLocalDniVo) {
		PersonaFisica persona = personaFisicaService.traerPersonaFisicaPorDni(ocupacionLocalDniVo.getDniPersona());
		
		if (persona == null) {
			PersonaFisicaVo personaVo = new PersonaFisicaVo();
			personaVo.setDni(ocupacionLocalDniVo.getDniPersona());
			persona = personaFisicaService.crearPersonaFisica(personaVo);
		}
		
		OcupacionLocalVo ocupacionVo = new OcupacionLocalVo();
		long idEmprendimiento = Long.valueOf(new String(Base64Utils.decodeFromString(idEmprendimientoBase64)));
		long idPersona = persona.getIdPersona();
		ocupacionVo.setIdEmprendimiento(idEmprendimiento);
		ocupacionVo.setIdPersona(idPersona);
		ocupacionVo.setUsuarioModi(ocupacionLocalDniVo.getUsuarioModi());

		OcupacionLocal ocupacion = repository.findByEmprendimientoPersona(idEmprendimiento, idPersona);

		if (ocupacion != null) {
			log.info("Se marca salida Ocupacion local con dni emprendimiento [{}]", ocupacion.getEmprendimiento().getNombre());
			ocupacion.setFechaHoraSalida(DateUtils.fechaHoy());
			ocupacion.setFechaModi(DateUtils.fechaHoy());
			ocupacion.setUsuarioModi(ocupacionLocalDniVo.getUsuarioModi());
		} else {
			log.info("Se marca salida Ocupacion local con dni emprendimiento [{}]", idEmprendimiento);
			ocupacion = new OcupacionLocal();
			ocupacion.setFechaHoraEntrada(DateUtils.fechaHoy());
			adaptVoToOcupacionLocalDni(ocupacion, ocupacionVo, persona);
		}

		try {
			ocupacion = repository.save(ocupacion);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return ocupacion;
	}
}
