package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.repositories.OcupacionLocalRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.OcupacionLocalVo;

@Service
@Transactional(readOnly = true)
public class OcupacionLocalService {

	@Autowired
	private OcupacionLocalRepository repository;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private EmprendimientoService emprendimientoService;

	public OcupacionLocal traerOcupacionLocalPorId(Long id) {
		return repository.findByIdOcupacionLocal(id);
	}

	public List<OcupacionLocal> traerTodos() {
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
		
		return repository.save(ocupacion);
	}

	@Transactional
	public OcupacionLocal crearOcupacionLocal(OcupacionLocalVo ocupacionLocalVo) {
		OcupacionLocal ocupacion = new OcupacionLocal();
		
		adaptVoToOcupacionLocal(ocupacion, ocupacionLocalVo);
		
		return repository.save(ocupacion);
	}
	
	private void adaptVoToOcupacionLocal(OcupacionLocal ocupacion, OcupacionLocalVo ocupacionLocalVo) {
		Persona persona = personaService.traerPersonaPorId(ocupacionLocalVo.getIdPersona());
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(ocupacionLocalVo.getIdEmprendimiento());
		
		ocupacion.setEmprendimiento(emprendimiento);
		ocupacion.setPersona(persona);
		ocupacion.setFechaHoraEntrada(ocupacionLocalVo.getFechaHoraEntrada());
		ocupacion.setFechaHoraSalida(ocupacionLocalVo.getFechaHoraSalida());
		ocupacion.setFechaModi(DateUtils.fechaHoy());
		ocupacion.setUsuarioModi(ocupacionLocalVo.getUsuarioModi());
	}

}
