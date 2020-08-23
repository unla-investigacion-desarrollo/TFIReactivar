package com.unla.alimentar.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.ArticuloReferencia;
import com.unla.alimentar.models.Categoria;
import com.unla.alimentar.models.Marca;
import com.unla.alimentar.models.UnidadMedida;
import com.unla.alimentar.repositories.ArticuloReferenciaRepository;
import com.unla.alimentar.vo.ArticuloReferenciaVo;

@Service
@Transactional(readOnly = true)
public class ArticuloReferenciaService {

	@Autowired
	private ArticuloReferenciaRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private MarcaService marcaService;

	@Autowired
	private UnidadMedidaService umService;
	
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

	@Transactional
	public ArticuloReferencia crearArticuloReferencia(ArticuloReferenciaVo articuloReferenciaVo) {
		ArticuloReferencia articulo = new ArticuloReferencia();
		
		adaptVoToArticuloReferencia(articulo, articuloReferenciaVo);
		
		return repository.save(articulo);
	}
	
	@Transactional
	public ArticuloReferencia actualizarArticuloReferencia(Long id, ArticuloReferenciaVo articuloReferenciaVo) {
		ArticuloReferencia articulo = repository.findByIdArticuloReferencia(id);
		
		if(articulo == null) {
			throw new ObjectNotFound("ArticuloReferencia");
		}
		
		adaptVoToArticuloReferencia(articulo, articuloReferenciaVo);
		
		return repository.save(articulo);
	}
	
	private void adaptVoToArticuloReferencia(ArticuloReferencia articulo, ArticuloReferenciaVo articuloReferenciaVo) {
		
		Categoria categoria = categoriaService.traerCategoriaPorId(articuloReferenciaVo.getIdCategoria());
		Marca marca = marcaService.traerMarcaPorId(articuloReferenciaVo.getIdMarca());
		UnidadMedida unidadMedida = umService.traerUnidadMedidaPorId(articuloReferenciaVo.getIdUnidadMedida());
		
		if(categoria == null || marca == null || unidadMedida == null) {
			throw new ObjectNotFound("Categoria / Marca / UnidadMedida");
		}
		
		articulo.setCategoria(categoria);
		articulo.setMarca(marca);
		articulo.setUnidadMedida(unidadMedida);
		articulo.setCodBarra(articuloReferenciaVo.getCodBarra());
		articulo.setDescripcion(articuloReferenciaVo.getDescripcion());
		articulo.setFechaModi(new Date(System.currentTimeMillis()));
		articulo.setFoto(articuloReferenciaVo.getFoto());
		articulo.setNombre(articuloReferenciaVo.getNombre());
		articulo.setPeso(articuloReferenciaVo.getPeso());
		articulo.setPrecioRefencia(articuloReferenciaVo.getPrecioRefencia());
		articulo.setUsuarioModi(articuloReferenciaVo.getUsuarioModi());
	}

}
