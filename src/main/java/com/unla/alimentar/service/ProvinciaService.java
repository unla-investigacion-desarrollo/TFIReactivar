package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Provincia;
import com.unla.alimentar.repository.ProvinciaRepository;

@Service
@Transactional(readOnly = true)
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository repository;
	
	public Provincia traerProvinciaPorId(Long id) {
		return repository.findByIdProvincia(id);
	}
	
	public List<Provincia> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarProvincia(long id) {
		Provincia provincia = repository.findByIdProvincia(id);
		
		if(provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		
		repository.delete(provincia);
	}
	
}
