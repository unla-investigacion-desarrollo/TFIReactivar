package com.unla.reactivar.controllers;

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

import com.unla.reactivar.models.Carrito;
import com.unla.reactivar.services.CarritoService;
import com.unla.reactivar.vo.CarritoVo;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.ReqPutCarritoVo;

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
	@ApiOperation(value = "Listar todos los carritos", notes = "Servicio para listar todos los carritos.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Carritos encontrados."),
			@ApiResponse(code = 404, message = "Carritos no encontrados.") })
	public List<Carrito> traerTodos() {
		return service.traerTodosCarritos();
	}

	@GetMapping("/{idCarrito}")
	@ApiOperation(value = "Mostrar un carrito por ID", notes = "Servicio para mostrar un carrito a partir de un ID.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Carrito encontrado"),
			@ApiResponse(code = 404, message = "Carrito no encontrado") })
	public Carrito traerCarrito(@PathVariable("idCarrito") long id) {
		return service.traerCarritoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Carrito", notes = "Servicio para crear carritos.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Carrito creado exitosamente."),
			@ApiResponse(code = 400, message = "No se pudo crear carrito.") })
	public ResponseEntity<Carrito> crearCarrito(@RequestBody CarritoVo carritoVo) {
		Carrito carrito = service.crearCarrito(carritoVo);

		return new ResponseEntity<>(carrito, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idCarrito}")
	@ApiOperation(value = "Eliminar carrito por ID", notes = "Servicio para eliminar un Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Carrito eliminado con exito"),
			@ApiResponse(code = 404, message = "Carrito no encontrado") })
	public ResponseEntity<Empty> eliminarCarrito(@PathVariable("idCarrito") long id) {

		service.borrarCarrito(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idCarrito}")
	@ApiOperation(value = "Modificar Carrito por ID", notes = "Servicio para modificar un Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Carrito modificado exitosamente"),
			@ApiResponse(code = 404, message = "Carrito no encontrado") })
	public ResponseEntity<Carrito> updateCarrito(@PathVariable("idCarrito") Long id, ReqPutCarritoVo carritoVo) {

		return new ResponseEntity<>(service.actualizarCarrito(id, carritoVo), HttpStatus.OK);
	}

}
