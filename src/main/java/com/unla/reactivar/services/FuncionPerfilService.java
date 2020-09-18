package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.models.FuncionPerfil;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.repositories.FuncionPerfilRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.FuncionPerfilVo;

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

		if (funcion == null) {
			throw new ObjectNotFound("Funcion");
		}

		adaptVoToFuncionPerfil(funcion, funcionPerfilVo);

		try {
			funcion = repository.save(funcion);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return funcion;
	}

	@Transactional
	public FuncionPerfil crearFuncionPerfil(FuncionPerfilVo funcionPerfilVo) {
		FuncionPerfil funcion = new FuncionPerfil();

		adaptVoToFuncionPerfil(funcion, funcionPerfilVo);

		try {
			funcion = repository.save(funcion);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return funcion;
	}

	private void adaptVoToFuncionPerfil(FuncionPerfil funcion, FuncionPerfilVo funcionPerfilVo) {
		Perfil perfil = perfilService.traerPerfilPorId(funcionPerfilVo.getIdPerfil());
		Funcion func = funcionService.traerFuncionPorId(funcionPerfilVo.getIdFuncion());

		if (perfil == null || funcion == null) {
			throw new ObjectNotFound("Perfil / Funcion");
		}

		funcion.setEdicion(funcionPerfilVo.isEdicion());
		funcion.setFechaModi(DateUtils.fechaHoy());
		funcion.setFuncion(func);
		funcion.setPerfil(perfil);
		funcion.setUsuarioModi(funcionPerfilVo.getUsuarioModi());
	}

}
