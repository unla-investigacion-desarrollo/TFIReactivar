package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.EmprendimientoRepository;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.EmprendimientoVo;

@Service
@Transactional(readOnly = true)
public class EmprendimientoService {

	@Autowired
	private EmprendimientoRepository repository;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private RubroService rubroService;

	@Autowired
	private TipoEmprendimientoService tipoEmprendimientoService;

	@Autowired
	private UbicacionService ubicacionService;

	public Emprendimiento traerEmprendimientoPorId(Long id) {
		return repository.findByIdEmprendimiento(id);
	}

	public List<Emprendimiento> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public Emprendimiento crearEmprendimiento(EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = new Emprendimiento();

		adaptarEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		return repository.save(emprendimiento);
	}

	@Transactional
	public void borrarEmprendimiento(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		repository.delete(emprendimiento);
	}

	@Transactional
	public Emprendimiento actualizarEmprendimiento(Long id, EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}
		
		Ubicacion ubicacion = ubicacionService.crearUbicacion(emprendimientoVo.getUbicacionVo());
		emprendimiento.setUbicacion(ubicacion);

		adaptarEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		return repository.save(emprendimiento);
	}

	private void adaptarEmprendimientoVoAEmprendimiento(EmprendimientoVo emprendimientoVo, Emprendimiento emprendimiento) {
		for (int i = 0; i < emprendimientoVo.getConfiguracionLocales().size(); i++) {
			emprendimiento.getConfiguracionLocales()
					.add(adaptarConfiguracionLocal(emprendimientoVo.getConfiguracionLocales().get(i)));
			emprendimiento.getConfiguracionLocales().get(i).setEmprendimiento(emprendimiento);
		}
		emprendimiento.setCuit(emprendimientoVo.getCuit());
		emprendimiento.setNombre(emprendimientoVo.getNombre());
		Persona persona = personaService.traerPersonaPorId(emprendimientoVo.getIdPersona());
		Rubro rubro = rubroService.traerRubroPorId(emprendimientoVo.getIdRubro());
		TipoEmprendimiento tipoEmprendimiento = tipoEmprendimientoService
				.traerTipoEmprendimientoPorId(emprendimientoVo.getIdTipoEmprendimiento());

		if (persona == null || rubro == null || tipoEmprendimiento == null) {
			throw new ObjectNotFound("Persona, rubro o tipoEmprendimiento");
		}

		emprendimiento.setPersona(persona);
		emprendimiento.setRubro(rubro);
		emprendimiento.setTipoEmprendimiento(tipoEmprendimiento);

	}

	private ConfiguracionLocal adaptarConfiguracionLocal(ConfiguracionLocalVo configuracionLocales) {

		ConfiguracionLocal config = new ConfiguracionLocal();
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());

		return config;
	}

}
