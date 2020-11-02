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
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.repositories.ConfiguracionLocalRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.ReqPostConfiguracionLocalVo;

@Service
@Transactional(readOnly = true)
public class ConfiguracionLocalService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ConfiguracionLocalRepository repository;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public ConfiguracionLocal traerConfiguracionLocalPorId(Long id) {
		log.info("Se traer configuracion local por id");
		return repository.findByIdConfiguracionLocal(id);
	}

	public List<ConfiguracionLocal> traerTodasConfiguracionesLocales() {
		log.info("Se traeran todas las cfg locales");
		return repository.findAll();
	}

	public List<ConfiguracionLocal> traerTodasConfiguracionesLocalesPorEmprendimiento(long idEmprendimiento) {
		log.info("Se traeran todas las cfg locales de un emprendimiento");
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(idEmprendimiento);
		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		return repository.findByEmprendimiento(emprendimiento);
	}

	@Transactional
	public ConfiguracionLocal crearConfiguracion(ReqPostConfiguracionLocalVo configuracionLocalVo) {
		ConfiguracionLocal config = new ConfiguracionLocal();

		adaptPostVoToConfiguracionLocal(configuracionLocalVo, config);

		try {
			log.info("Se creara cfg local");
			config = repository.save(config);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento crear una cfg local ya existente");
				throw new ObjectAlreadyExists();
			}
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
			log.error("Se obtuvo un error al intentar borrar la cfg local [{}]", id);
			throw new ObjectNotFound("ConfiguracionLocal");
		}
		log.info("Se eliminara la configuracion local ID: [{}]", configuracionLocal.getIdConfiguracionLocal());
		repository.delete(configuracionLocal);
	}

	@Transactional
	public void borrarListaConfiguracionLocal(long id) {
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(id);
		List<ConfiguracionLocal> configuracionLocal = repository.traerConfiguracionesLocal(emprendimiento);

		if (configuracionLocal.isEmpty() == true || configuracionLocal == null) {
			log.error("Se obtuvo un error al intentar borrar las cfg local para el emprendimiento [{}]", id);
			throw new ObjectNotFound("ConfiguracionLocal");
		}

		for (int i = 0; i < configuracionLocal.size(); i++) {
			log.info("Se eliminara la configuracion local id: [{}]", configuracionLocal.get(i).getIdConfiguracionLocal());
			repository.delete(configuracionLocal.get(i));
		}

	}

	@Transactional
	public ConfiguracionLocal actualizarConfiguracionLocal(Long id, ConfiguracionLocalVo configuracionLocalVo) {
		ConfiguracionLocal configuracionLocal = repository.findByIdConfiguracionLocal(id);

		if (configuracionLocal == null) {
			log.error("Se obtuvo un error al intentar actualizar la cfg local [{}]", id);
			throw new ObjectNotFound("ConfiguracionLocal");
		}

		adaptVoToConfiguracionLocal(configuracionLocalVo, configuracionLocal);

		try {
			log.info("Se actualizara cfg local");
			configuracionLocal = repository.save(configuracionLocal);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
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
