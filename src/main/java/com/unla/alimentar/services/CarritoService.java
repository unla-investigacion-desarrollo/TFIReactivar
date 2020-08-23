package com.unla.alimentar.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Carrito;
import com.unla.alimentar.models.Emprendimiento;
import com.unla.alimentar.models.EstadoCarrito;
import com.unla.alimentar.models.ItemCarrito;
import com.unla.alimentar.models.Persona;
import com.unla.alimentar.repositories.CarritoRepository;
import com.unla.alimentar.vo.CarritoVo;

@Service
@Transactional(readOnly = true)
public class CarritoService {

	@Autowired
	private CarritoRepository repository;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private EstadoCarritoService estadoCarritoService;
	
	@Autowired
	private ItemCarritoService itemCarritoService;
	
	@Autowired 
	private EmprendimientoService emprendimientoService;

	public Carrito traerCarritoPorId(Long id) {
		return repository.findByIdCarrito(id);
	}

	public List<Carrito> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarCarrito(long id) {
		Carrito registro = repository.findByIdCarrito(id);

		if (registro == null) {
			throw new ObjectNotFound("Carrito");
		}

		repository.delete(registro);
	}

	@Transactional
	public Carrito crearCarrito(CarritoVo carritoVo) {
		Carrito carrito = new Carrito();
		
		adaptVoToCarrito(carrito, carritoVo);
		
		return carrito;
	}

	@Transactional
	public Carrito actualizarCarrito(Long id, CarritoVo carritoVo) {
		Carrito carrito = repository.findByIdCarrito(id);

		if(carrito == null) {
			throw new ObjectNotFound("Carrito");
		}
		
		adaptVoToCarrito(carrito, carritoVo);
		
		return carrito;
	}

	private void adaptVoToCarrito(Carrito carrito, CarritoVo carritoVo) {
		
		Persona persona = personaService.traerPersonaPorId(carritoVo.getIdPersona());
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(carritoVo.getIdEmprendimiento());
		EstadoCarrito estadoCarrito = estadoCarritoService.traerEstadoCarritoPorId(carritoVo.getIdEstadoCarrito());

		if(persona == null || emprendimiento == null || estadoCarrito == null) {
			throw new ObjectNotFound("Persona / Emprendimiento / Estado carrito");
		}
		
		for(int i = 0; i < carritoVo.getListaItemCarrito().size() ; i++ ) {
			ItemCarrito item = itemCarritoService.traerItemCarritoPorId(carritoVo.getListaItemCarrito().get(i).getIdArticulo());
			
			if(item == null) {
				throw new ObjectNotFound("ItemCarrito");
			}
			
			carrito.getListaItemCarrito().add(item);
		}
		
		carrito.setPersona(persona);
		carrito.setEmprendimiento(emprendimiento);
		carrito.setEstadoCarrito(estadoCarrito);
		carrito.setFechaHora(new Date(System.currentTimeMillis()));
	
	}

}
