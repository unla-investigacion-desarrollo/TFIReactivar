package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.repositories.ConfiguracionLocalRepository;
import com.unla.reactivar.vo.ConfiguracionLocalVo;

@Service
@Transactional(readOnly = true)
public class ConfiguracionLocalService {

	@Autowired
	private ConfiguracionLocalRepository repository;

	public ConfiguracionLocal traerConfiguracionLocalPorId(Long id) {
		return repository.findByIdConfiguracionLocal(id);
	}

	public List<ConfiguracionLocal> traerTodasConfiguracionesLocales() {
		return repository.findAll();
	}

	@Transactional
	public ConfiguracionLocal crearConfiguracion(ConfiguracionLocalVo configuracionLocales) {
		ConfiguracionLocal config = new ConfiguracionLocal();

		adaptVoToConfiguracionLocal(configuracionLocales, config);

		try {
			config = repository.save(config);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return config;
	}

	private void adaptVoToConfiguracionLocal(ConfiguracionLocalVo configuracionLocales, ConfiguracionLocal config) {
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());
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

}
