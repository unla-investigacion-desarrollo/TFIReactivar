package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Rubro;
import com.unla.alimentar.repositories.RubroRepository;
import com.unla.alimentar.vo.RubroVo;

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

	public Rubro actualizarRubro(Long id, RubroVo rubroVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Rubro crearRubro(RubroVo rubroVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
