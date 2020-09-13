package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.repositories.UnidadMedidaRepository;
import com.unla.reactivar.vo.UnidadMedidaVo;

@Service
@Transactional(readOnly = true)
public class UnidadMedidaService {

	@Autowired
	private UnidadMedidaRepository repository;

	public UnidadMedida traerUnidadMedidaPorId(Long id) {
		return repository.findByIdUnidadMedida(id);
	}

	public List<UnidadMedida> traerTodasUnidadesMedida() {
		return repository.findAll();
	}

	@Transactional
	public void borrarUnidadMedida(long id) {
		UnidadMedida registro = repository.findByIdUnidadMedida(id);

		if (registro == null) {
			throw new ObjectNotFound("UnidadMedida");
		}

		repository.delete(registro);
	}

	@Transactional
	public UnidadMedida actualizarUnidadMedida(Long id, UnidadMedidaVo unidadMedidaVo) {
		UnidadMedida medida = repository.findByIdUnidadMedida(id);

		if (medida == null) {
			throw new ObjectNotFound("UnidadMedida");
		}

		medida.setNombre(unidadMedidaVo.getUnidadMedida());

		try {
			medida = repository.save(medida);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return medida;
	}

	@Transactional
	public UnidadMedida crearUnidadMedida(UnidadMedidaVo unidadMedidaVo) {
		UnidadMedida medida = new UnidadMedida();

		medida.setNombre(unidadMedidaVo.getUnidadMedida());

		try {
			medida = repository.save(medida);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return medida;
	}

}
