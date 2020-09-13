package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.EstadoTurno;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Turno;
import com.unla.reactivar.repositories.TurnoRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.TurnoVo;

@Service
@Transactional(readOnly = true)
public class TurnoService {

	@Autowired
	private TurnoRepository repository;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private EmprendimientoService emprendimientoService;
	
	@Autowired
	private EstadoTurnoService estadoService;

	public Turno traerTurnoPorId(Long id) {
		return repository.findByIdTurno(id);
	}

	public List<Turno> traerTodosTurnos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarTurno(long id) {
		Turno registro = repository.findByIdTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("Turno");
		}

		repository.delete(registro);
	}

	@Transactional
	public Turno actualizarTurno(Long id, TurnoVo turnoVo) {
		Turno turno = repository.findByIdTurno(id);

		if (turno == null) {
			throw new ObjectNotFound("Turno");
		}
		
		adaptVoToTurno(turno, turnoVo);
		
		try {
			turno = repository.save(turno);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return turno;
	}

	@Transactional
	public Turno crearTurno(TurnoVo turnoVo) {
		Turno turno = new Turno();
			
		adaptVoToTurno(turno, turnoVo);
		
		try {
			turno = repository.save(turno);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return turno;
	}
	
	private void adaptVoToTurno(Turno turno, TurnoVo turnoVo) {
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(turnoVo.getIdEmprendimiento());
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(turnoVo.getIdEstadoTurno());
		Persona persona = personaService.traerPersonaPorId(turnoVo.getIdPersona());
		
		if(emprendimiento == null || estadoTurno == null || persona == null) {
			throw new ObjectNotFound("Emprendimiento / EstadoTurno / Persona");
		}
		
		turno.setEmprendimiento(emprendimiento);
		turno.setEstadoTurno(estadoTurno);
		turno.setFechaModi(DateUtils.fechaHoy());
		turno.setFechaHora(turnoVo.getFechaHora());
		turno.setObservaciones(turnoVo.getObservaciones());
		turno.setPersona(persona);
		turno.setUsuarioModi(turnoVo.getUsuarioModi());
	}

}
