package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.EstadoTurno;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Turno;
import com.unla.reactivar.repositories.TurnoRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.utils.EnumDiaSemana;
import com.unla.reactivar.vo.TurnoVo;

@Service
@Transactional(readOnly = true)
public class TurnoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	private static final long OCUPADO = 2L;
	
	@Autowired
	private TurnoRepository repository;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	@Autowired
	private EstadoTurnoService estadoService;
	
	@Autowired
	private ConfiguracionLocalService configuracionLocalService;

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
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(OCUPADO);
		Persona persona = personaService.traerPersonaPorId(turnoVo.getIdPersona());

		if (emprendimiento == null || estadoTurno == null || persona == null) {
			throw new ObjectNotFound("Emprendimiento / EstadoTurno (Ocupado = 2) / Persona");
		}

		turno.setEmprendimiento(emprendimiento);
		turno.setEstadoTurno(estadoTurno);
		turno.setFechaModi(DateUtils.fechaHoy());
		turno.setFechaHora(turnoVo.getFechaHora());
		turno.setObservaciones(turnoVo.getObservaciones());
		turno.setPersona(persona);
		turno.setUsuarioModi(turnoVo.getUsuarioModi());
	}
	
	public List<Turno> traerTurnosPorEmprendimiento(long idEmp, long idEst) {
		List<Turno> turnos = new ArrayList<>();
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(idEmp);
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(idEst);
		if (emprendimiento == null || estadoTurno == null) {
			throw new ObjectNotFound("Emprendimiento / EstadoTurno");
		}
		turnos = repository.findTurnosPorEmprendimiento(emprendimiento, estadoTurno);
		return turnos;
	}
	
	public Map<String, List<String>> traerTurnosDisponibles(long idEmprendimiento, String dia) {
		DateFormat format = new SimpleDateFormat("HH:mm");
		Map<String, List<String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		List<ConfiguracionLocal> configuraciones = configuracionLocalService
				.traerTodasConfiguracionesLocalesPorEmprendimiento(idEmprendimiento);
		List<Turno> turnos = traerTurnosPorEmprendimiento(idEmprendimiento, OCUPADO);
		for (ConfiguracionLocal config : configuraciones) {
			if(!config.getDiaSemana().equalsIgnoreCase(dia))
				continue;
			if (!map.containsKey(config.getDiaSemana())) {
				List<String> horas =  new ArrayList<>();
				int intervalo = config.getIntervaloTurnos();
				LocalTime turnoInicio1 = LocalTime.parse(config.getTurno1Desde());
				LocalTime turnoFin1 = LocalTime.parse(config.getTurno1Hasta());
				LocalTime turnoInicio2 = LocalTime.parse(config.getTurno2Desde());
				LocalTime turnoFin2 = LocalTime.parse(config.getTurno2Hasta());
				while(turnoInicio1.isBefore(turnoFin1) || turnoInicio1.isEqual(turnoFin1)) {
					String hora = turnoInicio1.toString("HH:mm");
					horas.add(hora);
					turnoInicio1 = turnoInicio1.plusMinutes(intervalo);
				}
				while(turnoInicio2.isBefore(turnoFin2) || turnoInicio2.isEqual(turnoFin2)) {
					String hora = turnoInicio2.toString("HH:mm");
					horas.add(hora);
					turnoInicio2 = turnoInicio2.plusMinutes(intervalo);
				}
				map.put(config.getDiaSemana(), horas);
			}
		}
		Calendar calendar = Calendar.getInstance();
		for(Turno turno : turnos) {
			calendar.setTime(turno.getFechaHora());
			int nroDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
			String diaSemana = EnumDiaSemana.getByNroDia(nroDiaSemana).getDiaKey();
			String horaTurno = new SimpleDateFormat("HH:mm").format(turno.getFechaHora().getTime() + 10800000);
			if(map.containsKey(diaSemana))
				map.get(diaSemana).remove(horaTurno);
		}
		
		return map;
	}

}
