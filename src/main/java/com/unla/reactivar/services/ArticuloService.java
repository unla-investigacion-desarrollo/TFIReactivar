package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Articulo;
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Marca;
import com.unla.reactivar.models.Promocion;
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

	public Articulo traerArticuloPorId(Long id) {
		return repository.findByIdArticulo(id);
	}

	public List<Articulo> traerTodosArticulos() {
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

		try {
			articulo = repository.save(articulo);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return articulo;
	}

	@Transactional
	public Articulo actualizarArticulo(Long id, ArticuloVo articuloVo) {
		Articulo articulo = repository.findByIdArticulo(id);

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

	private void adaptVoToArticulo(Articulo articulo, ArticuloVo articuloVo) {

		Categoria categoria = categoriaService.traerCategoriaPorId(articuloVo.getIdCategoria());
		Marca marca = marcaService.traerMarcaPorId(articuloVo.getIdMarca());
		UnidadMedida unidadMedida = umService.traerUnidadMedidaPorId(articuloVo.getIdUnidadMedida());
		Emprendimiento emprendimiento = empService.traerEmprendimientoPorId(articuloVo.getIdEmprendimiento());
		Promocion promocion = promocionService.traerPromocionPorId(articuloVo.getIdPromocion());

		if (categoria == null || marca == null || unidadMedida == null || emprendimiento == null) {
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
