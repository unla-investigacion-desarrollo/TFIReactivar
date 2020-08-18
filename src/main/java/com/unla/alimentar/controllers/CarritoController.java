package com.unla.alimentar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.alimentar.models.Carrito;
import com.unla.alimentar.services.CarritoService;
import com.unla.alimentar.vo.CarritoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/carrito")
@Api(tags = "Carrito")
public class CarritoController {

	@Autowired
	private CarritoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los carritos", notes = "Service para listar todos los carritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Carritos encontrados"),
			@ApiResponse(code = 404, message = "Carritos no encontrados") })
	public List<Carrito> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idCarrito}")
	@ApiOperation(value = "Mostrar un carrito", notes = "Service para mostrar un carrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Carrito encontrado"),
			@ApiResponse(code = 404, message = "Carrito no encontrado") })
	public Carrito traerCarrito(@PathVariable("idCarrito") long id) {
		return service.traerCarritoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Carrito", notes = "Servicio creador de carritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Carrito successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Carrito> crearCarrito(@RequestBody CarritoVo carritoVo) {
		Carrito carrito = service.crearCarrito(carritoVo);

		return new ResponseEntity<>(carrito, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idCarrito}")
	@ApiOperation(value = "Eliminar carrito", notes = "Servicio elimina Carrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Carrito eliminado con exito"),
			@ApiResponse(code = 404, message = "Carrito no encontrado") })
	public void eliminarCarrito(@PathVariable("idCarrito") long id) {

		service.borrarCarrito(id);
	}

	@PutMapping("/{idCarrito}")
	@ApiOperation(value = "Update Carrito", notes = "Carrito updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Carrito successfully updated"),
			@ApiResponse(code = 404, message = "Carrito not found") })
	public ResponseEntity<Carrito> updateAbility(@PathVariable("idCarrito") Long id,
			CarritoVo carritoVo) {

		return new ResponseEntity<>(service.actualizarCarrito(id, carritoVo), HttpStatus.OK);
	}

}
