package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Provincia;
import com.unla.alimentar.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository repository;
	
	public Provincia traerProvinciaPorId(Long id) {
		return repository.findByIdProvincia(id);
	}
	
}
