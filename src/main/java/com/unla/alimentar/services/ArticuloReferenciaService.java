package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.ArticuloReferencia;
import com.unla.alimentar.repositories.ArticuloReferenciaRepository;
import com.unla.alimentar.vo.ArticuloReferenciaVo;

@Service
@Transactional(readOnly = true)
public class ArticuloReferenciaService {

	@Autowired
	private ArticuloReferenciaRepository repository;

	public ArticuloReferencia traerArticuloReferenciaPorId(Long id) {
		return repository.findByIdArticuloReferencia(id);
	}

	public List<ArticuloReferencia> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarArticuloReferencia(long id) {
		ArticuloReferencia articuloReferencia = repository.findByIdArticuloReferencia(id);

		if (articuloReferencia == null) {
			throw new ObjectNotFound("ArticuloReferencia");
		}

		repository.delete(articuloReferencia);
	}

	public ArticuloReferencia crearArticuloReferencia(ArticuloReferenciaVo articuloReferenciaVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArticuloReferencia actualizarArticuloReferencia(Long id, ArticuloReferenciaVo articuloReferenciaVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
