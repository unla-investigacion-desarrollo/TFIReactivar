package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.repositories.OcupacionLocalRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.OcupacionLocalVo;

@Service
@Transactional(readOnly = true)
public class OcupacionLocalService {

	@Autowired
	private OcupacionLocalRepository repository;

	@Autowired
	private PersonaFisicaService personaFisicaService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public OcupacionLocal traerOcupacionLocalPorId(Long id) {
		return repository.findByIdOcupacionLocal(id);
	}

	public List<OcupacionLocal> traerTodasOcupacionesLocales() {
		return repository.findAll();
	}

	@Transactional
	public void borrarOcupacionLocal(long id) {
		OcupacionLocal registro = repository.findByIdOcupacionLocal(id);

		if (registro == null) {
			throw new ObjectNotFound("OcupacionLocal");
		}

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
			ocupacion.setFechaHoraSalida(DateUtils.fechaHoy());
			ocupacion.setFechaModi(DateUtils.fechaHoy());
			ocupacion.setUsuarioModi(ocupacionLocalVo.getUsuarioModi());
		} else {
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

	public List<OcupacionLocal> traerOcupacionEntreFechas(Date fechaInicio, Date fechaFin, long idPersona) {
		List<OcupacionLocal> ocupacionesLocal = repository.findByFechaDesdeHasta(fechaInicio, fechaFin);
		List<Long> listaEmprendimientosVisitados = new ArrayList<>();
		List<OcupacionLocal> ocupacionesLocalFiltrado = new ArrayList<>();
	
		for(OcupacionLocal ocupacion : ocupacionesLocal) {
			if(ocupacion.getPersona().getIdPersona() == idPersona) {
				listaEmprendimientosVisitados.add(ocupacion.getEmprendimiento().getIdEmprendimiento());
			}
		}
		listaEmprendimientosVisitados = listaEmprendimientosVisitados.stream()
	     .distinct()
	     .collect(Collectors.toList());
		for(OcupacionLocal ocupacion : ocupacionesLocal) {
			for(long idEmprendimiento : listaEmprendimientosVisitados) {
				if (ocupacion.getEmprendimiento().getIdEmprendimiento() == idEmprendimiento) {
					ocupacionesLocalFiltrado.add(ocupacion);
	                break;
	            }
			}
		}
		
		return ocupacionesLocalFiltrado;
	}
	
	public List<OcupacionLocal> traerOcupacionesSinSalida(){
		return repository.findByFechaHoraSalidaIsNull();
	}

	@Transactional
	public void actualizarFechaSalidaVacia(OcupacionLocal ocupacion) {
		repository.save(ocupacion);
	}
}
