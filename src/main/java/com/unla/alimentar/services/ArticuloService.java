package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Articulo;
import com.unla.alimentar.models.Categoria;
import com.unla.alimentar.models.Marca;
import com.unla.alimentar.models.UnidadMedida;
import com.unla.alimentar.repositories.ArticuloRepository;
import com.unla.alimentar.utils.DateUtils;
import com.unla.alimentar.vo.ArticuloVo;

@Service
@Transactional(readOnly = true)
public class ArticuloService {

	@Autowired
	private ArticuloRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private MarcaService marcaService;

	@Autowired
	private UnidadMedidaService umService;
	
	public Articulo traerArticuloPorId(Long id) {
		return repository.findByIdArticulo(id);
	}

	public List<Articulo> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarArticulo(long id) {
		Articulo articulo = repository.findByIdArticulo(id);

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		repository.delete(articulo);
	}

	@Transactional
	public Articulo crearArticulo(ArticuloVo articuloVo) {
		Articulo articulo = new Articulo();
		
		adaptVoToArticulo(articulo, articuloVo);
		
		return repository.save(articulo);
	}

	@Transactional
	public Articulo actualizarArticulo(Long id, ArticuloVo articuloVo) {
		Articulo articulo = repository.findByIdArticulo(id);
		
		if(articulo == null) {
			throw new ObjectNotFound("Articulo");
		}
		
		adaptVoToArticulo(articulo, articuloVo);
		
		return repository.save(articulo);
	}
	
	private void adaptVoToArticulo(Articulo articulo, ArticuloVo articuloVo) {
		
		Categoria categoria = categoriaService.traerCategoriaPorId(articuloVo.getIdCategoria());
		Marca marca = marcaService.traerMarcaPorId(articuloVo.getIdMarca());
		UnidadMedida unidadMedida = umService.traerUnidadMedidaPorId(articuloVo.getIdUnidadMedida());
		
		if(categoria == null || marca == null || unidadMedida == null) {
			throw new ObjectNotFound("Categoria / Marca / UnidadMedida");
		}
		
		articulo.setCategoria(categoria);
		articulo.setMarca(marca);
		articulo.setUnidadMedida(unidadMedida);
		articulo.setCodBarra(articuloVo.getCodBarra());
		articulo.setDescripcion(articuloVo.getDescripcion());
		articulo.setFechaModi(DateUtils.fechaHoy());
		articulo.setFoto(articuloVo.getFoto());
		articulo.setNombre(articuloVo.getNombre());
		articulo.setPeso(articuloVo.getPeso());
		articulo.setPrecio(articuloVo.getPrecio());
		articulo.setUsuarioModi(articuloVo.getUsuarioModi());
		articulo.setActivoComercial(articuloVo.isActivoComercial());
		articulo.setVisible(articuloVo.isVisible());

	}


}
