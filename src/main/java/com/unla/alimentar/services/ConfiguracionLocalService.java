package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.ConfiguracionLocal;
import com.unla.alimentar.repositories.ConfiguracionLocalRepository;
import com.unla.alimentar.vo.ConfiguracionLocalVo;

@Service
public class ConfiguracionLocalService {

	@Autowired
	private ConfiguracionLocalRepository repository;
	
	public ConfiguracionLocal traerConfiguracionLocalPorId(Long id) {
		return repository.findByIdConfiguracionLocal(id);
	}
	
	public List<ConfiguracionLocal> traerTodos(){
		return repository.findAll();
	}

	public ConfiguracionLocal crearConfiguracion(ConfiguracionLocalVo configuracionLocales) {
		
		ConfiguracionLocal config = new ConfiguracionLocal();
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());

		return repository.save(config);
	}
	
	public void borrarConfiguracionLocal(long id) {
		ConfiguracionLocal configuracionLocal = repository.findByIdConfiguracionLocal(id);

		if (configuracionLocal == null) {
			throw new ObjectNotFound("ConfiguracionLocal");
		}

		repository.delete(configuracionLocal);
	}

	public ConfiguracionLocal actualizarConfiguracionLocal(Long id, ConfiguracionLocalVo configuracionLocalVo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
