package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.models.FuncionPerfil;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.repositories.PerfilRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.PerfilVo;

@Service
@Transactional(readOnly = true)
public class PerfilService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private PerfilRepository repository;

	public Perfil traerPerfilPorId(long idPerfil) {
		log.info("Se traeran perfil por id");
		return repository.findByIdPerfil(idPerfil);
	}

	public List<Perfil> traerTodos() {
		log.info("Se traeran todos los perfiles");
		return repository.findAll();
	}

	@Transactional
	public void borrarPerfil(long id) {
		Perfil perfil = repository.findByIdPerfil(id);

		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		log.info("Se eliminara perfil [{}]",id);

		repository.delete(perfil);
	}

	@Transactional
	public Perfil actualizarPerfil(Long id, PerfilVo perfilVo) {
		Perfil perfil = repository.findByIdPerfil(id);

		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}

		adaptVoToPerfil(perfil, perfilVo);

		try {
			log.info("Se actualizara perfil [{}]", perfil.getNombre());
			perfil = repository.save(perfil);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return perfil;
	}

	@Transactional
	public Perfil crearPerfil(PerfilVo perfilVo) {
		Perfil perfil = new Perfil();

		adaptVoToPerfil(perfil, perfilVo);

		try {
			log.info("Se creara perfil [{}]", perfil.getNombre());
			perfil = repository.save(perfil);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return perfil;
	}

	private void adaptVoToPerfil(Perfil perfil, PerfilVo perfilVo) {
		perfil.setNombre(perfilVo.getNombre());
		perfil.setFechaModi(DateUtils.fechaHoy());
		perfil.setUsuarioModi(perfilVo.getUsuarioModi());
	}

	public List<Funcion> traerFuncionesPorPerfil(long id) {
		log.info("Se traeran funciones por perfil");
		Perfil perfil = repository.findByIdPerfil(id);
		
		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		
		List<Funcion> funciones = new ArrayList<>();
		for(FuncionPerfil fp : perfil.getFuncionesPerfil()) {
			funciones.add(fp.getFuncion());
		}
		return funciones;
	}
}
