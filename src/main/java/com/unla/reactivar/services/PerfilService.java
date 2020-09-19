package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.repositories.PerfilRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.PerfilVo;

@Service
@Transactional(readOnly = true)
public class PerfilService {

	@Autowired
	private PerfilRepository repository;

	public Perfil traerPerfilPorId(long idPerfil) {
		return repository.findByIdPerfil(idPerfil);
	}

	public List<Perfil> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarPerfil(long id) {
		Perfil perfil = repository.findByIdPerfil(id);

		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}

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
			perfil = repository.save(perfil);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return perfil;
	}

	@Transactional
	public Perfil crearPerfil(PerfilVo perfilVo) {
		Perfil perfil = new Perfil();

		adaptVoToPerfil(perfil, perfilVo);

		try {
			perfil = repository.save(perfil);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return perfil;
	}

	private void adaptVoToPerfil(Perfil perfil, PerfilVo perfilVo) {
		perfil.setNombre(perfilVo.getNombre());
		perfil.setFechaModi(DateUtils.fechaHoy());
		perfil.setUsuarioModi(perfilVo.getUsuarioModi());
	}
}
