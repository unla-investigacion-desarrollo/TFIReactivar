package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Perfil;
import com.unla.alimentar.repository.PerfilRepository;

@Service
public class PerfilService {
	
	@Autowired
	private PerfilRepository repository;
	
	public Perfil traerPerfilPorId(long idPerfil){
		return repository.findByIdPerfil(idPerfil);
	}

}
