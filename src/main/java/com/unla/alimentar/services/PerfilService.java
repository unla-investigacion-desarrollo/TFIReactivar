package com.unla.alimentar.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Perfil;
import com.unla.alimentar.repositories.PerfilRepository;
import com.unla.alimentar.vo.PerfilVo;

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

	@Transactional
	public Perfil actualizarPerfil(Long id, PerfilVo perfilVo) {
		Perfil perfil = repository.findByIdPerfil(id);
		
		if(perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		
		adaptVoToPerfil(perfil, perfilVo);
		
		return repository.save(perfil);
	}

	@Transactional
	public Perfil crearPerfil(PerfilVo perfilVo) {	
		Perfil perfil = new Perfil();
		
		adaptVoToPerfil(perfil, perfilVo);
		
		return repository.save(perfil);
	}
	
	private void adaptVoToPerfil(Perfil perfil, PerfilVo perfilVo) {
		perfil.setNombre(perfilVo.getNombre());
		perfil.setFechaModi(new Date());
		perfil.setUsuarioModi(perfilVo.getUsuarioModi());
	}
}
