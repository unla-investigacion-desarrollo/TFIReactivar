package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Rubro;
import com.unla.alimentar.repository.RubroRepository;

@Service
public class RubroService {

	@Autowired
	private RubroRepository rubroRepository;
	
	public Rubro traerRubroPorId(String id) {
		Rubro rubro = rubroRepository.findByIdRubro(id);
		return rubro;
	}

}
