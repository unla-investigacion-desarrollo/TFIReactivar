package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Perfil;
import com.unla.alimentar.repositories.PerfilRepository;

@Service
@Transactional(readOnly = true)
public class PerfilService {
	
	@Autowired
	private PerfilRepository repository;
	
	public Perfil traerPerfilPorId(long idPerfil){
		return repository.findByIdPerfil(idPerfil);
	}
	
	public List<Perfil> traerTodos(){
		return repository.findAll();
	}

	@Transactional
	public void borrarPerfil(long id) {
		Perfil perfil = repository.findByIdPerfil(id);
		
		if(perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		
		repository.delete(perfil);
	}
}
