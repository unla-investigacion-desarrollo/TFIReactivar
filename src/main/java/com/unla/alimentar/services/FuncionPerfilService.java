package com.unla.alimentar.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Funcion;
import com.unla.alimentar.models.FuncionPerfil;
import com.unla.alimentar.models.Perfil;
import com.unla.alimentar.repositories.FuncionPerfilRepository;
import com.unla.alimentar.vo.FuncionPerfilVo;

@Service
@Transactional(readOnly = true)
public class FuncionPerfilService {

	@Autowired
	private FuncionPerfilRepository repository;
	
	@Autowired
	private FuncionService funcionService;
	
	@Autowired
	private PerfilService perfilService;

	public FuncionPerfil traerFuncionPerfilPorId(Long id) {
		return repository.findByIdFuncionPerfil(id);
	}

	public List<FuncionPerfil> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarFuncionPerfil(long id) {
		FuncionPerfil registro = repository.findByIdFuncionPerfil(id);

		if (registro == null) {
			throw new ObjectNotFound("FuncionPerfil");
		}

		repository.delete(registro);
	}

	@Transactional
	public FuncionPerfil actualizarFuncionPerfil(Long id, FuncionPerfilVo funcionPerfilVo) {
		FuncionPerfil funcion = repository.findByIdFuncionPerfil(id);
		
		if(funcion == null) {
			throw new ObjectNotFound("Funcion");
		}
		
		adaptVoToFuncionPerfil(funcion, funcionPerfilVo);
		
		return repository.save(funcion);
	}

	@Transactional
	public FuncionPerfil crearFuncionPerfil(FuncionPerfilVo funcionPerfilVo) {
		FuncionPerfil funcion = new FuncionPerfil();
		
		adaptVoToFuncionPerfil(funcion, funcionPerfilVo);
		
		return repository.save(funcion);
	}
	
	private void adaptVoToFuncionPerfil(FuncionPerfil funcion, FuncionPerfilVo funcionPerfilVo) {
		Perfil perfil = perfilService.traerPerfilPorId(funcionPerfilVo.getIdPerfil());
		Funcion func = funcionService.traerFuncionPorId(funcionPerfilVo.getIdFuncion());
		
		if(perfil == null || funcion == null) {
			throw new ObjectNotFound("Perfil / Funcion");
		}
		
		funcion.setEdicion(funcionPerfilVo.isEdicion());
		funcion.setFechaModi(new Date());
		funcion.setFuncion(func);
		funcion.setPerfil(perfil);
		funcion.setUsuarioModi(funcionPerfilVo.getUsuarioModi());
	}

}
