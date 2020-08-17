package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.TipoEmprendimiento;
import com.unla.alimentar.repository.TipoEmprendimientoRepository;

@Service
public class TipoEmprendimientoService {

	@Autowired
	private TipoEmprendimientoRepository repository;
	
	public TipoEmprendimiento traerTipoEmprendimientoporId(Long id) {
		return repository.findByIdTipoEmprendimiento(id);
	}
	
}
