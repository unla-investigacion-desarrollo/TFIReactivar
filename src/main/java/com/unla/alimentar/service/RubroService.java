package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Rubro;
import com.unla.alimentar.repository.RubroRepository;

@Service
@Transactional(readOnly = true)
public class RubroService {

	@Autowired
	private RubroRepository repository;
	
	public Rubro traerRubroPorId(Long id) {
		Rubro rubro = repository.findByIdRubro(id);
		return rubro;
	}
	
	public List<Rubro> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarRubro(long id) {
		Rubro rubro = repository.findByIdRubro(id);
		
		if(rubro == null) {
			throw new ObjectNotFound("Rubro");
		}
		
		repository.delete(rubro);
	}

}
