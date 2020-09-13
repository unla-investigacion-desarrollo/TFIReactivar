package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Marca;
import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.models.ReqArticulo;
import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.repositories.ArticuloRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.ArticuloVo;

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

	@Autowired
	private EmprendimientoService empService;

	@Autowired
	private PromocionService promocionService;

	public ReqArticulo traerArticuloPorId(Long id) {
		return repository.findByIdArticulo(id);
	}

	public List<ReqArticulo> traerTodosArticulos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarArticulo(long id) {
		ReqArticulo articulo = repository.findByIdArticulo(id);

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		repository.delete(articulo);
	}

	@Transactional
	public ReqArticulo crearArticulo(ArticuloVo articuloVo) {
		ReqArticulo articulo = new ReqArticulo();

		adaptVoToArticulo(articulo, articuloVo);

		try {
			articulo = repository.save(articulo);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return articulo;
	}

	@Transactional
	public ReqArticulo actualizarArticulo(Long id, ArticuloVo articuloVo) {
		ReqArticulo articulo = repository.findByIdArticulo(id);

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		adaptVoToArticulo(articulo, articuloVo);

		try {
			articulo = repository.save(articulo);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return articulo;
	}

	private void adaptVoToArticulo(ReqArticulo articulo, ArticuloVo articuloVo) {

		Categoria categoria = categoriaService.traerCategoriaPorId(articuloVo.getIdCategoria());
		Marca marca = marcaService.traerMarcaPorId(articuloVo.getIdMarca());
		UnidadMedida unidadMedida = umService.traerUnidadMedidaPorId(articuloVo.getIdUnidadMedida());
		Emprendimiento emprendimiento = empService.traerEmprendimientoPorId(articuloVo.getIdEmprendimiento());
		Promocion promocion = promocionService.traerPromocionPorId(articuloVo.getIdPromocion());

		if (categoria == null || marca == null || unidadMedida == null || emprendimiento == null ) {
			throw new ObjectNotFound("Categoria / Marca / UnidadMedida / Emprendimiento ");
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
		articulo.setEmprendimiento(emprendimiento);
		articulo.setPromocion(promocion);

	}

}
