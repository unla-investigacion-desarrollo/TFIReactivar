package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.FuncionPerfil;
import com.unla.alimentar.repositories.FuncionPerfilRepository;
import com.unla.alimentar.vo.FuncionPerfilVo;

@Service
@Transactional(readOnly = true)
public class FuncionPerfilService {

	@Autowired
	private FuncionPerfilRepository repository;

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

	public FuncionPerfil actualizarFuncionPerfil(Long id, FuncionPerfilVo funcionPerfilVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public FuncionPerfil crearFuncionPerfil(FuncionPerfilVo funcionPerfilVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
