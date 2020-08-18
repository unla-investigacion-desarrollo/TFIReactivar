package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.OcupacionLocal;
import com.unla.alimentar.repositories.OcupacionLocalRepository;
import com.unla.alimentar.vo.OcupacionLocalVo;

@Service
@Transactional(readOnly = true)
public class OcupacionLocalService {

	@Autowired
	private OcupacionLocalRepository repository;

	public OcupacionLocal traerOcupacionLocalPorId(Long id) {
		return repository.findByIdOcupacionLocal(id);
	}

	public List<OcupacionLocal> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarOcupacionLocal(long id) {
		OcupacionLocal registro = repository.findByIdOcupacionLocal(id);

		if (registro == null) {
			throw new ObjectNotFound("OcupacionLocal");
		}

		repository.delete(registro);
	}

	public OcupacionLocal actualizarOcupacionLocal(Long id, OcupacionLocalVo ocupacionLocalVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public OcupacionLocal crearOcupacionLocal(OcupacionLocalVo ocupacionLocalVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
