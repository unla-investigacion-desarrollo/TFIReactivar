package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Localidad;
import com.unla.alimentar.repository.LocalidadRepository;

@Service
@Transactional(readOnly = true)
public class LocalidadService {

	@Autowired
	private LocalidadRepository repository;
	
	public Localidad traerLocalidadPorId(Long id) {
		return repository.findByIdLocalidad(id);
	}
	
	public List<Localidad> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarLocalidad(long id) {
		Localidad localidad = repository.findByIdLocalidad(id);
		
		if(localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		
		repository.delete(localidad);
	}
}
