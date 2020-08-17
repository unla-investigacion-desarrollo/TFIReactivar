package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Localidad;
import com.unla.alimentar.repository.LocalidadRepository;

@Service
public class LocalidadService {

	@Autowired
	private LocalidadRepository repository;
	
	public Localidad traerLocalidadPorId(Long id) {
		return repository.findByIdLocalidad(id);
	}
	
}
