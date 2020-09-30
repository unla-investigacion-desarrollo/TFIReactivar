package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.ArticuloReferencia;
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.models.Marca;
import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.repositories.ArticuloReferenciaRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.ReqArticuloReferenciaVo;

@Service
@Transactional(readOnly = true)
public class ArticuloReferenciaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ArticuloReferenciaRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private UnidadMedidaService umService;

	public ArticuloReferencia traerArticuloReferenciaPorId(Long id) {
		log.info("Se traera un articulo referencia por id");
		return repository.findByIdArticuloReferencia(id);
	}

	public List<ArticuloReferencia> traerTodosArticulosReferencia() {
		log.info("Se traeran todos los articulos referencia");
		return repository.findAll();
	}

	@Transactional
	public void borrarArticuloReferencia(long id) {
		ArticuloReferencia articuloReferencia = repository.findByIdArticuloReferencia(id);

		if (articuloReferencia == null) {
			log.error("Se obtuvo un error al intentar borrar el articulo [{}]", id);
			throw new ObjectNotFound("ArticuloReferencia");
		}

		log.info("Se eliminara articulo referencia [{}]", articuloReferencia.getNombre());
		repository.delete(articuloReferencia);
	}

	@Transactional
	public ArticuloReferencia crearArticuloReferencia(ReqArticuloReferenciaVo articuloReferenciaVo) {
		ArticuloReferencia articulo = new ArticuloReferencia();

		adaptVoToArticuloReferencia(articulo, articuloReferenciaVo);
		log.info("Se creara articulo referencia [{}]", articulo.getNombre());
		try {
			articulo = repository.save(articulo);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento guardar un articulo ya existente [{}]", articuloReferenciaVo.getCodBarra());
				throw new ObjectAlreadyExists();
			}
		}
		return articulo;
	}

	@Transactional
	public ArticuloReferencia actualizarArticuloReferencia(Long id, ReqArticuloReferenciaVo articuloReferenciaVo) {
		ArticuloReferencia articulo = repository.findByIdArticuloReferencia(id);

		if (articulo == null) {
			log.error("No se encontro el objeto en la bd - Articulo Referencia");
			throw new ObjectNotFound("ArticuloReferencia");
		}

		adaptVoToArticuloReferencia(articulo, articuloReferenciaVo);
		log.info("Se actualizara articulo referencia [{}]", articulo.getNombre());
		try {
			articulo = repository.save(articulo);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento actualizar a un articulo ya existente [{}]", articuloReferenciaVo.getCodBarra());
				throw new ObjectAlreadyExists();
			}
		}
		return articulo;
	}

	private void adaptVoToArticuloReferencia(ArticuloReferencia articulo,
			ReqArticuloReferenciaVo articuloReferenciaVo) {

		Categoria categoria = categoriaService.traerCategoriaPorId(articuloReferenciaVo.getIdCategoria());
		Marca marca = marcaService.traerMarcaPorId(articuloReferenciaVo.getIdMarca());
		UnidadMedida unidadMedida = umService.traerUnidadMedidaPorId(articuloReferenciaVo.getIdUnidadMedida());

		if (categoria == null || marca == null || unidadMedida == null) {
			log.error("No se encontro el objeto en la bd - Categoria - Marca - UnidadMedida");
			throw new ObjectNotFound("Categoria / Marca / UnidadMedida");
		}

		articulo.setCategoria(categoria);
		articulo.setMarca(marca);
		articulo.setUnidadMedida(unidadMedida);
		articulo.setCodBarra(articuloReferenciaVo.getCodBarra());
		articulo.setDescripcion(articuloReferenciaVo.getDescripcion());
		articulo.setFechaModi(DateUtils.fechaHoy());
		articulo.setFoto(articuloReferenciaVo.getFoto());
		articulo.setNombre(articuloReferenciaVo.getNombre());
		articulo.setPeso(articuloReferenciaVo.getPeso());
		articulo.setPrecioRefencia(articuloReferenciaVo.getPrecioRefencia());
		articulo.setUsuarioModi(articuloReferenciaVo.getUsuarioModi());
	}

}
