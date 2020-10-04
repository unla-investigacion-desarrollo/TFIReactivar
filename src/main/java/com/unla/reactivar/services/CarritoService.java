package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Carrito;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.EstadoCarrito;
import com.unla.reactivar.models.ItemCarrito;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.repositories.CarritoRepository;
import com.unla.reactivar.vo.CarritoVo;
import com.unla.reactivar.vo.ItemCarritoVo;
import com.unla.reactivar.vo.ReqPutCarritoVo;

@Service
@Transactional(readOnly = true)
public class CarritoService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private CarritoRepository repository;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private EstadoCarritoService estadoCarritoService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	@Autowired
	private ArticuloService articuloService;

	public Carrito traerCarritoPorId(Long id) {
		log.info("Se traera un carrito por id");
		return repository.findByIdCarrito(id);
	}

	public List<Carrito> traerTodosCarritos() {
		log.info("Se traeran todos los carritos");
		return repository.findAll();
	}

	@Transactional
	public void borrarCarrito(long id) {
		Carrito registro = repository.findByIdCarrito(id);

		if (registro == null) {
			log.error("Se obtuvo un error al intentar borrar el carrito [{}]", id);
			throw new ObjectNotFound("Carrito");
		}

		log.info("Se eliminara articulo [{}]", registro.getIdCarrito());
		repository.delete(registro);
	}

	@Transactional
	public Carrito crearCarrito(CarritoVo carritoVo) {
		Carrito carrito = new Carrito();

		adaptVoToCarrito(carrito, carritoVo);

		try {
			log.info("Se creara carrito");
			carrito = repository.save(carrito);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento crear un carrito ya existente");
				throw new ObjectAlreadyExists();
			}
		}

		return carrito;
	}

	@Transactional
	public Carrito actualizarCarrito(Long id, ReqPutCarritoVo carritoVo) {
		Carrito carrito = repository.findByIdCarrito(id);
		EstadoCarrito estadoCarrito = estadoCarritoService.traerEstadoCarritoPorId(carritoVo.getIdEstadoCarrito());

		if (estadoCarrito == null || carrito == null) {
			log.error("Se obtuvo un error al intentar actualizar el carrito [{}]", id);
			throw new ObjectNotFound("Carrito / EstadoCarrito");
		}

		carrito.setEstadoCarrito(estadoCarrito);

		try {
			log.info("Se actualizara carrito");
			carrito = repository.save(carrito);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				log.error("Se intento actualizar un carrito ya existente");
				throw new ObjectAlreadyExists();
			}
		}

		return carrito;
	}

	private void adaptVoToCarrito(Carrito carrito, CarritoVo carritoVo) {

		List<ItemCarrito> listItemCarrito = new ArrayList<>();

		Persona persona = personaService.traerPersonaPorId(carritoVo.getIdPersona());
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(carritoVo.getIdEmprendimiento());
		EstadoCarrito estadoCarrito = estadoCarritoService.traerEstadoCarritoPorId(carritoVo.getIdEstadoCarrito());

		if (persona == null || emprendimiento == null || estadoCarrito == null) {
			throw new ObjectNotFound("Persona / Emprendimiento / Estado carrito");
		}

		for (int i = 0; i < carritoVo.getListaItemCarrito().size(); i++) {

			listItemCarrito.add(adaptVoToItemCarrito(carritoVo.getListaItemCarrito().get(i)));
			listItemCarrito.get(i).setCarrito(carrito);
		}

		carrito.setListaItemCarrito(listItemCarrito);
		carrito.setPersona(persona);
		carrito.setEmprendimiento(emprendimiento);
		carrito.setEstadoCarrito(estadoCarrito);
		carrito.setFechaHora(new Date(System.currentTimeMillis()));

	}

	private ItemCarrito adaptVoToItemCarrito(ItemCarritoVo itemCarritoVo) {
		ItemCarrito item = new ItemCarrito();
		item.setArticuloPrecio(articuloService.traerArticuloPorId(itemCarritoVo.getIdArticulo()));
		item.setCantidad(itemCarritoVo.getCantidad());
		return item;
	}

}
