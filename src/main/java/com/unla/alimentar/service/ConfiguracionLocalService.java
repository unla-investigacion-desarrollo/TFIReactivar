package com.unla.alimentar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.ConfiguracionLocal;
import com.unla.alimentar.repository.ConfiguracionLocalRepository;
import com.unla.alimentar.vo.ConfiguracionLocalVo;
import com.unla.alimentar.vo.EmprendimientoVo;

@Service
public class ConfiguracionLocalService {

	@Autowired
	private ConfiguracionLocalRepository repository;
	
	public ConfiguracionLocal traerConfiguracionLocalPorId(Long id) {
		return repository.findByIdConfiguracionLocal(id);
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
	
	
}
