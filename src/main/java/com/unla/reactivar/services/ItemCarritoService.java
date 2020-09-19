package com.unla.reactivar.services;

import java.util.List;

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

	@Autowired
	private ItemCarritoRepository repository;

	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private CarritoService carritoService;

	public ItemCarrito traerItemCarritoPorId(Long id) {
		return repository.findByIdItemCarrito(id);
	}

	public List<ItemCarrito> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarItemCarrito(long id) {
		ItemCarrito registro = repository.findByIdItemCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("ItemCarrito");
		}

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
			item = repository.save(item);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return item;
	}

	@Transactional
	public ItemCarrito crearItemCarrito(ReqPostItemCarritoVo itemCarritoVo) {
		ItemCarrito item = new ItemCarrito();

		adaptPostVoToItemCarrito(item, itemCarritoVo);

		try {
			item = repository.save(item);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		item = repository.save(item);

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
