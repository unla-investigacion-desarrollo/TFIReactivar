package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Provincia;
import com.unla.alimentar.repositories.ProvinciaRepository;
import com.unla.alimentar.vo.ProvinciaVo;

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

	public Provincia actualizarProvincia(Long id, ProvinciaVo provinciaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Provincia crearProvincia(ProvinciaVo provinciaVo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
