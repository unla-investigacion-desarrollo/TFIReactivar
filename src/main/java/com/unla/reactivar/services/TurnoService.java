package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import com.unla.reactivar.vo.ReqPatchTurnoVo;
import com.unla.reactivar.vo.TurnoVo;

@Service
@Transactional(readOnly = true)
public class TurnoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	private static final long OCUPADO = 2L;
	private static final long PENDIENTE = 3L;

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
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(PENDIENTE);
		Persona persona = personaService.traerPersonaPorId(turnoVo.getIdPersona());

		if (emprendimiento == null || estadoTurno == null || persona == null) {
			throw new ObjectNotFound("Emprendimiento / EstadoTurno (Pendiente = 3) / Persona");
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

	public Map<String, List<String>> traerTurnosDisponiblesXFecha(long idEmprendimiento, String fecha) {
		log.info("Se consultaran los turnos disponibles de la fecha elegida");
		DateFormat format = new SimpleDateFormat("HH:mm");
		Map<String, List<String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		List<ConfiguracionLocal> configuraciones = configuracionLocalService
				.traerTodasConfiguracionesLocalesPorEmprendimiento(idEmprendimiento);
		List<Turno> turnosOcupados = traerTurnosPorEmprendimiento(idEmprendimiento, OCUPADO);
		List<Turno> turnosPendientes = traerTurnosPorEmprendimiento(idEmprendimiento, PENDIENTE);

		// String fecha = "23/10/2020"
		GregorianCalendar fechaDia = traerFecha2(fecha);
		String dia = traerDiaDeLaSemana(fechaDia);
		log.info("dia [{}]", dia);

		for (ConfiguracionLocal config : configuraciones) {
			if (!config.getDiaSemana().equalsIgnoreCase(dia))
				continue;
			if (!map.containsKey(config.getDiaSemana())) {
				List<String> horas = new ArrayList<>();
				int intervalo = config.getIntervaloTurnos();
				LocalTime turnoInicio1 = LocalTime.parse(config.getTurno1Desde());
				LocalTime turnoFin1 = LocalTime.parse(config.getTurno1Hasta());
				while (turnoInicio1.isBefore(turnoFin1) || turnoInicio1.isEqual(turnoFin1)) {
					String hora = turnoInicio1.toString("HH:mm");
					horas.add(hora);
					turnoInicio1 = turnoInicio1.plusMinutes(intervalo);
				}

				if (!config.getTurno2Desde().equals("--:--") && !config.getTurno2Hasta().equals("--:--")) {
					LocalTime turnoInicio2 = LocalTime.parse(config.getTurno2Desde());
					LocalTime turnoFin2 = LocalTime.parse(config.getTurno2Hasta());

					while (turnoInicio2.isBefore(turnoFin2) || turnoInicio2.isEqual(turnoFin2)) {
						String hora = turnoInicio2.toString("HH:mm");
						horas.add(hora);
						turnoInicio2 = turnoInicio2.plusMinutes(intervalo);
					}
				}

				map.put(config.getDiaSemana(), horas);
			}
		}
		Calendar calendar = Calendar.getInstance();
		for (Turno turno : turnosOcupados) {
			calendar.setTime(turno.getFechaHora());
			int nroDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
			String diaSemana = EnumDiaSemana.getByNroDia(nroDiaSemana).getDiaKey();
			String horaTurno = new SimpleDateFormat("HH:mm").format(turno.getFechaHora().getTime() + 10800000);
			if (map.containsKey(diaSemana))
				map.get(diaSemana).remove(horaTurno);
		}
		for (Turno turno : turnosPendientes) {
			calendar.setTime(turno.getFechaHora());
			int nroDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
			String diaSemana = EnumDiaSemana.getByNroDia(nroDiaSemana).getDiaKey();
			String horaTurno = new SimpleDateFormat("HH:mm").format(turno.getFechaHora().getTime() + 10800000);
			if (map.containsKey(diaSemana))
				map.get(diaSemana).remove(horaTurno);
		}

		return map;
	}

	public static GregorianCalendar traerFecha2(String fecha) {
		String d = fecha.substring(0, 2);
		String m = fecha.substring(3, 5);
		String a = fecha.substring(6, 10);

		int anio = Integer.valueOf(a);
		int mes = Integer.valueOf(m);
		int dia = Integer.valueOf(d);
		return new GregorianCalendar(anio, mes - 1, dia);
	}

	public static String traerDiaDeLaSemana(GregorianCalendar f) {
		String dia = "";

		switch (f.get(Calendar.DAY_OF_WEEK)) {

		case 1:
			dia = "7";
			break;

		case 2:
			dia = "1";
			break;

		case 3:
			dia = "2";
			break;

		case 4:
			dia = "3";
			break;

		case 5:
			dia = "4";
			break;

		case 6:
			dia = "5";
			break;

		case 7:
			dia = "6";
			break;
		}

		return dia;
	}

	@SuppressWarnings("deprecation")
	public List<Turno> traerTurnoPorPersona(long id, String fecha) {
		Persona persona = personaService.traerPersonaPorId(id);
		int d = Integer.valueOf(fecha.substring(0, 2));
		int m = Integer.valueOf(fecha.substring(3, 5));
		int a = Integer.valueOf(fecha.substring(6, 10));
		
		int mes = m-1;
		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}
		
		List<Turno> turnos = repository.findByPersona(persona);
		
		return turnos.stream().filter(x -> x.getFechaHora().getDate() == d).filter(x -> x.getFechaHora().getMonth() == mes).filter(x-> x.getFechaHora().getYear()+1900 == a).collect(Collectors.toList());
		
	}
	
	@SuppressWarnings("deprecation")
	public List<Turno> traerTurnoPorEmprendimiento(long id, String fecha) {
		Emprendimiento emp = emprendimientoService.traerEmprendimientoPorId(id);
		int d = Integer.valueOf(fecha.substring(0, 2));
		int m = Integer.valueOf(fecha.substring(3, 5));
		int a = Integer.valueOf(fecha.substring(6, 10));
		
		int mes = m-1;
		if (emp == null) {
			throw new ObjectNotFound("Persona");
		}
		
		List<Turno> turnos = repository.findByEmprendimiento(emp);
		
		return turnos.stream().filter(x -> x.getFechaHora().getDate() == d).filter(x -> x.getFechaHora().getMonth() == mes).filter(x-> x.getFechaHora().getYear()+1900 == a).collect(Collectors.toList());
		
	}


	public Turno patchTurno(Long id, ReqPatchTurnoVo patchTurno) {
		Turno turno = repository.findByIdTurno(id);
		EstadoTurno estadoTurno = estadoService.traerEstadoTurnoPorId(patchTurno.getIdEstadoTurno());

		if (turno == null || estadoTurno == null) {
			throw new ObjectNotFound("Turno / EstadoTurno");
		}
		
		turno.setEstadoTurno(estadoTurno);

		return repository.save(turno) ;
	}

}
