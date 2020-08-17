package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.TipoEmprendimiento;
import com.unla.alimentar.repository.TipoEmprendimientoRepository;

@Service
@Transactional(readOnly = true)
public class TipoEmprendimientoService {

	@Autowired
	private TipoEmprendimientoRepository repository;
	
	public TipoEmprendimiento traerTipoEmprendimientoporId(Long id) {
		return repository.findByIdTipoEmprendimiento(id);
	}
	
	public List<TipoEmprendimiento> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarTipoEmprendimiento(long id) {
		TipoEmprendimiento tipoEmprendimiento = repository.findByIdTipoEmprendimiento(id);
		
		if(tipoEmprendimiento == null) {
			throw new ObjectNotFound("TipoEmprendimiento");
		}
		
		repository.delete(tipoEmprendimiento);
	}
	
}
