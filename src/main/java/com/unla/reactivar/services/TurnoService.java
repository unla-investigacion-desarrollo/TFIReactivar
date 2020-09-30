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

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private TurnoRepository repository;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	@Autowired
	private EstadoTurnoService estadoService;

	public Turno traerTurnoPorId(Long id) {
		log.info("Se traera un turno por id");
		return repository.findByIdTurno(id);
	}

	public List<Turno> traerTodosTurnos() {
		log.info("Se traera todos los turnos");
		return repository.findAll();
	}

	@Transactional
	public void borrarTurno(long id) {
		Turno registro = repository.findByIdTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("Turno");
		}
		log.info("Se eliminara el turno [{}]", id);
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
			log.info("Se actualizara el turno [{}]", id);
			turno = repository.save(turno);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return turno;
	}

	@Transactional
	public Turno crearTurno(TurnoVo turnoVo) {
		Turno turno = new Turno();

		adaptVoToTurno(turno, turnoVo);

		try {
			log.info("Se creara un turno");
			turno = repository.save(turno);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return turno;
	}

	private void adaptVoToTurno(Turno turno, TurnoVo turnoVo) {
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(turnoVo.getIdEmprendimiento());
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(turnoVo.getIdEstadoTurno());
		Persona persona = personaService.traerPersonaPorId(turnoVo.getIdPersona());

		if (emprendimiento == null || estadoTurno == null || persona == null) {
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
