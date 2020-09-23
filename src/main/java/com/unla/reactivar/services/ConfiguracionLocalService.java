package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.repositories.ConfiguracionLocalRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.ReqPostConfiguracionLocalVo;

@Service
@Transactional(readOnly = true)
public class ConfiguracionLocalService {

	@Autowired
	private ConfiguracionLocalRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public ConfiguracionLocal traerConfiguracionLocalPorId(Long id) {
		return repository.findByIdConfiguracionLocal(id);
	}

	public List<ConfiguracionLocal> traerTodasConfiguracionesLocales() {
		return repository.findAll();
	}

	@Transactional
	public ConfiguracionLocal crearConfiguracion(ReqPostConfiguracionLocalVo configuracionLocalVo) {
		ConfiguracionLocal config = new ConfiguracionLocal();

		adaptPostVoToConfiguracionLocal(configuracionLocalVo, config);

		try {
			config = repository.save(config);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return config;
	}

	private void adaptPostVoToConfiguracionLocal(ReqPostConfiguracionLocalVo configuracionLocales,
			ConfiguracionLocal config) {
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(configuracionLocales.getIdEmprendimiento());
		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());
		config.setFechaModi(DateUtils.fechaHoy());
		config.setUsuarioModi(configuracionLocales.getUsuarioModi());
		config.setEmprendimiento(emprendimiento);
	}

	@Transactional
	public void borrarConfiguracionLocal(long id) {
		ConfiguracionLocal configuracionLocal = repository.findByIdConfiguracionLocal(id);

		if (configuracionLocal == null) {
			throw new ObjectNotFound("ConfiguracionLocal");
		}

		repository.delete(configuracionLocal);
	}

	@Transactional
	public ConfiguracionLocal actualizarConfiguracionLocal(Long id, ConfiguracionLocalVo configuracionLocalVo) {
		ConfiguracionLocal configuracionLocal = repository.findByIdConfiguracionLocal(id);

		if (configuracionLocal == null) {
			throw new ObjectNotFound("ConfiguracionLocal");
		}

		adaptVoToConfiguracionLocal(configuracionLocalVo, configuracionLocal);

		try {
			configuracionLocal = repository.save(configuracionLocal);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return configuracionLocal;
	}

	private void adaptVoToConfiguracionLocal(ConfiguracionLocalVo configuracionLocales, ConfiguracionLocal config) {
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());
		config.setFechaModi(DateUtils.fechaHoy());
		config.setUsuarioModi(configuracionLocales.getUsuarioModi());
	}
}
