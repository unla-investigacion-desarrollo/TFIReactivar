package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Localidad;
import com.unla.alimentar.repositories.LocalidadRepository;
import com.unla.alimentar.vo.LocalidadVo;

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

	public Localidad actualizarLocalidad(Long id, LocalidadVo localidadVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Localidad crearLocalidad(LocalidadVo localidadVo) {
		// TODO Auto-generated method stub
		return null;
	}
}
