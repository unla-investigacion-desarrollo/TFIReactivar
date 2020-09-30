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
import com.unla.reactivar.models.Articulo;
import com.unla.reactivar.models.Carrito;
import com.unla.reactivar.models.ItemCarrito;
import com.unla.reactivar.repositories.ItemCarritoRepository;
import com.unla.reactivar.vo.ItemCarritoVo;
import com.unla.reactivar.vo.ReqPostItemCarritoVo;

@Service
@Transactional(readOnly = true)
public class ItemCarritoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ItemCarritoRepository repository;

	@Autowired
	private ArticuloService articuloService;

	@Autowired
	private CarritoService carritoService;

	public ItemCarrito traerItemCarritoPorId(Long id) {
		log.info("Se traera item carrito por id");
		return repository.findByIdItemCarrito(id);
	}

	public List<ItemCarrito> traerTodosItemsCarrito() {
		log.info("Se traera todos los item carrito");
		return repository.findAll();
	}

	@Transactional
	public void borrarItemCarrito(long id) {
		ItemCarrito registro = repository.findByIdItemCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("ItemCarrito");
		}
		log.info("Se elimina item carrito");
		repository.delete(registro);
	}

	@Transactional
	public ItemCarrito actualizarItemCarrito(Long id, ItemCarritoVo itemCarritoVo) {
		ItemCarrito item = repository.findByIdItemCarrito(id);

		if (item == null) {
			throw new ObjectNotFound("ItemCarrito");
		}

		adaptVoToItemCarrito(item, itemCarritoVo);

		try {
			log.info("Se actualizara item carrito");
			item = repository.save(item);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return item;
	}

	@Transactional
	public ItemCarrito crearItemCarrito(ReqPostItemCarritoVo itemCarritoVo) {
		ItemCarrito item = new ItemCarrito();

		adaptPostVoToItemCarrito(item, itemCarritoVo);

		try {
			log.info("Se creara item carrito");
			item = repository.save(item);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return item;
	}

	private void adaptVoToItemCarrito(ItemCarrito item, ItemCarritoVo itemCarritoVo) {
		Articulo articulo = articuloService.traerArticuloPorId(itemCarritoVo.getIdArticulo());

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		item.setArticuloPrecio(articulo);
		item.setCantidad(itemCarritoVo.getCantidad());

	}

	private void adaptPostVoToItemCarrito(ItemCarrito item, ReqPostItemCarritoVo itemCarritoVo) {
		Articulo articulo = articuloService.traerArticuloPorId(itemCarritoVo.getIdArticulo());
		Carrito carrito = carritoService.traerCarritoPorId(itemCarritoVo.getIdCarrito());

		if (articulo == null) {
			throw new ObjectNotFound("Articulo");
		}

		item.setArticuloPrecio(articulo);
		item.setCantidad(itemCarritoVo.getCantidad());
		item.setCarrito(carrito);
	}

}
